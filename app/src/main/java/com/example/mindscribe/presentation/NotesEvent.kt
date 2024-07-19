package com.example.mindscribe.presentation

import com.example.mindscribe.data.Note

sealed interface NotesEvent
{
    object SortNotes : NotesEvent
    data class DeleteNote(var note: Note) : NotesEvent
    data class SaveNote(
        var title: String,
        var disp: String
    ) : NotesEvent

}