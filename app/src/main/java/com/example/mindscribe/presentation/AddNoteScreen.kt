package com.example.mindscribe.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindscribe.R

@Composable
fun AddNoteScreen (
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent)->Unit
)
{
    Scaffold(
        topBar =
        {
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
                    painter = painterResource(id = R.drawable.logo), // Load the logo bitmap
                    contentDescription = "MindScribe App Logo", // Add a content description for accessibility
                    modifier = Modifier
                        .size(60.dp) // adjust the size of the logo
                        .clip(CircleShape) // optional, to make the logo circular
                )

            }
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(
                    NotesEvent.SaveNote(
                        title = state.title.value,
                        disp = state.disp.value
                    )
                )
                navController.popBackStack()

            }) {
                Icon(imageVector = Icons.Rounded.Check , contentDescription =null)

                
            }
        },

        floatingActionButtonPosition = FabPosition.Center, // This is important to center the FAB

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
            // Add your background image here
            Image(
                painter = painterResource(id = R.drawable.addnotescreenimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )


            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
            {

                OutlinedTextField(
                    value = state.title.value, onValueChange = {
                        state.title.value = it
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

                //.............................................


                OutlinedTextField(
                    value = state.disp.value, onValueChange = {
                        state.disp.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),


                    placeholder = {
                        Text(text = "Description",fontWeight = FontWeight.Bold )

                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize =20.sp
                    )
                )

            }


        }

    }
}

