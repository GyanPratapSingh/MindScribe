package com.example.mindscribe.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
        topBar =
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Magenta)
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo), // Load the logo bitmap
                    contentDescription = "MindScribe App Logo", // Add a content description for accessibility
                    modifier = Modifier
                        .size(40.dp) // adjust the size of the logo
                        .clip(CircleShape) // optional, to make the logo circular
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
                        imageVector = Icons.AutoMirrored.Rounded.Sort, contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )

                }

            }
        },
        floatingActionButton =
        {
            FloatingActionButton(containerColor = Color.Red , onClick = {
                state.title.value = ""
                state.disp.value = ""
                navController.navigate("AddNoteScreen")

            })
            {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.Center // This is important to center the FAB
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
            // Add your background image here
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
                items(state.notes.size) {
                    NoteItem(
                        state = state,
                        index = it,
                        onEvent = onEvent
                    )
                }

            }

        }
    }
}

@Composable
fun NoteItem(state: NoteState, index: Int, onEvent: (NotesEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Pink80)
            .padding(12.dp)

    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.notes.get(index = index).title,
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(9.dp))
            Text(
                text = state.notes.get(index = index).disp,
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                color = Color.White
            )

        }
        IconButton(onClick = { onEvent(NotesEvent.DeleteNote(
            state.notes.get(index = index)

        )) }) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                modifier = Modifier.size(35.dp)

            )
        }
    }
}
