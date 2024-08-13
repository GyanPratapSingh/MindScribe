package com.example.mindscribe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mindscribe.data.NoteDatabase
import com.example.mindscribe.presentation.AddNoteScreen
import com.example.mindscribe.presentation.EditNoteScreen
import com.example.mindscribe.presentation.NoteScreen
import com.example.mindscribe.presentation.NoteState
import com.example.mindscribe.presentation.NoteViewModel
import com.example.mindscribe.ui.theme.MindScribeTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(database.dao) as T
                }
            }
        }
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MindScribeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val state: NoteState by viewModel.state.collectAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NotesScreen") {

                        composable("NotesScreen") {
                            NoteScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable("AddNoteScreen") {
                            AddNoteScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable("EditNoteScreen/{noteId}") { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getString("noteId")
                            EditNoteScreen(
                                state = state,
                                noteId = noteId ?: "",
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}
