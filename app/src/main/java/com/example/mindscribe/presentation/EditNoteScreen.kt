package com.example.mindscribe.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindscribe.R
@Composable
fun EditNoteScreen(
    state: NoteState,
    noteId: String, // Assuming noteId is a String; change to Int if needed
    navController: NavController,
    onEvent: (NotesEvent) -> Unit
) {
    val note = state.notes.find { it.id.toString() == noteId }
    if (note != null) {
        // Update state with note details if not already populated
        state.title.value = note.title
        state.disp.value = note.disp
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Magenta)
                    .padding(19.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MindScribe App",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "MindScribe App Logo",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (note != null) {
                    onEvent(
                        NotesEvent.UpdateNote(
                            id = noteId,
                            title = state.title.value,
                            disp = state.disp.value
                        )
                    )
                    navController.popBackStack()
                }
            }) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "Save")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color.Cyan)
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.editpage),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Edit Your Content",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )

                OutlinedTextField(
                    value = state.title.value,
                    onValueChange = { title ->
                        state.title.value = title
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = {
                        Text(text = "Title", fontWeight = FontWeight.Bold)
                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                )

                OutlinedTextField(
                    value = state.disp.value,
                    onValueChange = { description ->
                        state.disp.value = description
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = {
                        Text(text = "Description", fontWeight = FontWeight.Bold)
                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}
