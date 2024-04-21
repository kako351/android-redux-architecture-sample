package com.kako351.android_redux_architecture_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kako351.android_redux_architecture_sample.redux.AddTodoAction
import com.kako351.android_redux_architecture_sample.redux.AppState
import com.kako351.android_redux_architecture_sample.ui.theme.AndroidreduxarchitecturesampleTheme

class MainActivity : ComponentActivity() {
    val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidreduxarchitecturesampleTheme {
                val state = viewModel.uiState.collectAsState(initial = AppState.INITIAL).value
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                viewModel.dispatch(
                                    AddTodoAction(
                                        text = "add text!"
                                    )
                                )
                            },
                            content = {
                                Text(text = "Add")
                            }
                        )
                        when(state) {
                            is AppState.TODOS -> {
                                LazyColumn {
                                    items(state.todos) { todo ->
                                        Text(text = todo.text)
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}
