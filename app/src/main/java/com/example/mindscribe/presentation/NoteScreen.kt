package com.example.mindscribe.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindscribe.R
import com.example.mindscribe.ui.theme.Pink40
import com.example.mindscribe.ui.theme.Pink80

@Composable
fun NoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Magenta)
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "MindScribe App Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = " MindScribe",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
                IconButton(onClick = { onEvent(NotesEvent.SortNotes) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Sort,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(containerColor = Color.Red, onClick = {
                state.title.value = ""
                state.disp.value = ""
                navController.navigate("AddNoteScreen")
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color.Magenta)
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.notescreenimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            LazyColumn(
                contentPadding = it,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.notes.size) { index ->
                    NoteItem(
                        state = state,
                        index = index,
                        navController = navController,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun NoteItem(
    state: NoteState,
    index: Int,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Pink80)
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.notes[index].title,
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(9.dp))
            Text(
                text = state.notes[index].disp,
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                color = Color.White
            )
        }

        IconButton(onClick = {
            navController.navigate("EditNoteScreen/${state.notes[index].id}")
        }) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edit Note",
                modifier = Modifier.size(35.dp),
                tint = Color.Cyan
            )
        }

        IconButton(onClick = {
            onEvent(NotesEvent.DeleteNote(state.notes[index]))
        }) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}
