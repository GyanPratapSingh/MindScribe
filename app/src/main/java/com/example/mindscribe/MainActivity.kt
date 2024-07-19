package com.example.mindscribe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mindscribe.data.NoteDatabase
import com.example.mindscribe.presentation.AddNoteScreen
import com.example.mindscribe.presentation.NoteScreen
import com.example.mindscribe.presentation.NoteState
import com.example.mindscribe.presentation.NoteViewModel
import com.example.mindscribe.ui.theme.MindScribeTheme

class MainActivity : ComponentActivity()
{

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }

     private val viewModel by viewModels<NoteViewModel>(
         factoryProducer = {
             object : ViewModelProvider.Factory{
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                     return NoteViewModel(database.dao) as T
                 }
             }
         }

     )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MindScribeTheme {




                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val state: NoteState by  viewModel.state.collectAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NotesScreen" ){

                        composable("NotesScreen"){
                            NoteScreen(state = state, navController = navController, onEvent =viewModel::onEvent )
                        }
                        composable("AddNoteScreen"){
                            AddNoteScreen(state = state, navController = navController, onEvent =viewModel::onEvent )
                        }


                    }
                 }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MindScribeTheme {
        Greeting("Android")
    }
}