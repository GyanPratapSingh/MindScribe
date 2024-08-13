package com.example.mindscribe.presentation

import com.example.mindscribe.data.Note

sealed interface NotesEvent {

    data object SortNotes : NotesEvent

    data class EditNote(val note: Note) : NotesEvent

    data class DeleteNote(val note: Note) : NotesEvent

    data class UpdateNote(val id: String, val title: String, val disp: String) : NotesEvent

    data class SaveNote(val title: String, val disp: String) : NotesEvent
}
