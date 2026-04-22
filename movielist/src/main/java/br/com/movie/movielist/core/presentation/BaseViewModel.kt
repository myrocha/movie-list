package br.com.movie.movielist.core.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

interface UiAction

interface UiEvent

abstract class BaseViewModel<S, A : UiAction, E : UiEvent>(initialState: S) : ViewModel() {
    protected val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected val _event = Channel<E>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()
}

interface ActionHandler<A : UiAction> {
    fun onAction(action: A)
}