package com.example.mindscribe.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mindscribe.data.Note

 data class NoteState
    (
    val notes : List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val disp: MutableState<String> = mutableStateOf("")
   )