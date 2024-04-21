package com.kako351.android_redux_architecture_sample

import com.kako351.android_redux_architecture_sample.model.Todo
import com.kako351.android_redux_architecture_sample.redux.Action
import com.kako351.android_redux_architecture_sample.redux.AddTodoAction
import com.kako351.android_redux_architecture_sample.redux.AppState
import com.kako351.android_redux_architecture_sample.redux.SetVisibilityFilterAction
import com.kako351.android_redux_architecture_sample.redux.ToggleTodoAction
import com.kako351.android_redux_architecture_sample.redux.VisibilityFilters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.reduxkotlin.Reducer
import org.reduxkotlin.ReducerForActionType
import org.reduxkotlin.createThreadSafeStore


class MainViewModel() {
    val reducer: Reducer<AppState> = {state, action ->
        //do work
        rootReducer(state, action)
    }
    val store = createThreadSafeStore(reducer, AppState.INITIAL).apply {
        subscribe {
            _uiState.update {
                state
            }
        }
    }
    private val _uiState = MutableStateFlow<AppState>(AppState.INITIAL)
    val uiState = _uiState.asStateFlow()

    fun todosReducer(state: List<Todo>, action: Any) =
        when (action) {
            is AddTodoAction -> state.plus(
                Todo(
                    text = action.text,
                    completed = false
                )
            )
            is ToggleTodoAction -> state.mapIndexed { index, todo ->
                if(index == action.index) {
                    todo.copy(completed = !todo.completed)
                } else {
                    todo
                }
            }
            else -> state
        }

    fun visibilityFilterReducer(state: VisibilityFilters, action: Any): VisibilityFilters =
        when (action) {
            is SetVisibilityFilterAction -> action.filter
            else -> state
        }

    fun rootReducer(state: AppState, action: Any): AppState = when(state) {
        is AppState.TODOS -> AppState.TODOS(
            todos = todosReducer(state.todos, action),
            visibilityFilter = visibilityFilterReducer(state.visibilityFilter, action)
        )
        is AppState.INITIAL -> AppState.TODOS()
    }

    fun dispatch(action: Action) {
        store.dispatch(action)
    }
}