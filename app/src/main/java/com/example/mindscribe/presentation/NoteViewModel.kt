package com.example.mindscribe.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindscribe.data.Note
import com.example.mindscribe.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private val dao: NoteDao
) : ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    private val notes = isSortedByDateAdded.flatMapLatest {
        if (it) {
            dao.getOrderedByDateAddedBy()
        } else {
            dao.getOrderedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(NoteState())
    val state = combine(_state, isSortedByDateAdded, notes) { state, _, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    try {
                        dao.deleteNote(event.note)
                    } catch (e: Exception) {
                        Log.e("NoteViewModel", "Error deleting note: ${e.message}", e)
                    }
                }
            }
            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = event.title,
                    disp = event.disp,
                    dateAdded = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    try {
                        dao.upsertNote(note)
                    } catch (e: Exception) {
                        Log.e("NoteViewModel", "Error saving note: ${e.message}", e)
                    }
                    _state.update {
                        it.copy(
                            title = mutableStateOf(""),
                            disp = mutableStateOf("")
                        )
                    }
                }
            }
            is NotesEvent.UpdateNote -> {
                viewModelScope.launch {
                    try {
                        dao.updateNote(
                            Note(
                                id = event.id.toInt(), // Assuming id is a string representation of an integer
                                title = event.title,
                                disp = event.disp,
                                dateAdded = System.currentTimeMillis() // You might want to retrieve the original date instead
                            )
                        )
                    } catch (e: Exception) {
                        Log.e("NoteViewModel", "Error updating note: ${e.message}", e)
                    }
                }
            }
            NotesEvent.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
            is NotesEvent.EditNote -> {
                _state.update {
                    it.copy(
                        title = mutableStateOf(event.note.title),
                        disp = mutableStateOf(event.note.disp)
                    )
                }
            }
            else -> {
                Log.e("NoteViewModel", "Unknown event: $event")
                throw IllegalArgumentException("Unknown event: $event")
            }
        }
    }
}
