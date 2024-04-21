package com.kako351.android_redux_architecture_sample.redux

import com.kako351.android_redux_architecture_sample.model.Todo

sealed class AppState {
    data class TODOS(
        val visibilityFilter: VisibilityFilters = VisibilityFilters.SHOW_ALL,
        val todos: List<Todo> = listOf(
            Todo(
                text = "Consider using Redux",
                completed = true
            ),
            Todo(
                text = "Keep all state in a single tree",
                completed = false
            )
        )
    ): AppState()

    object INITIAL: AppState()
}
