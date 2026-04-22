package br.com.movie.movielist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.movie.movielist.core.presentation.ActionHandler
import br.com.movie.movielist.core.presentation.BaseViewModel
import br.com.movie.movielist.core.presentation.UiAction
import br.com.movie.movielist.core.presentation.UiEvent
import br.com.movie.movielist.domain.usecase.GetMyListsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getMyListsUseCase: GetMyListsUseCase
) : BaseViewModel<MovieListUiState, MovieListViewModel.Action, MovieListViewModel.Event>(
    MovieListUiState()
), ActionHandler<MovieListViewModel.Action> {

    private var fetchJob: Job? = null

    override fun onAction(action: Action) {
        when (action) {
            is Action.LoadMovies -> loadMovies()
            is Action.Retry -> loadMovies()
            is Action.OnMovieClick -> {
                viewModelScope.launch {
                    _event.send(Event.NavigateToDetail(action.movieId))
                }
            }
        }
    }

    private fun loadMovies() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getMyListsUseCase(page = 1, language = "en-US")
                .handleStates(_uiState) { movies ->
                    _uiState.update { it.copy(movies = movies, isLoading = false) }
                }
                .collect()
        }
    }

    sealed interface Action : UiAction {
        data object LoadMovies : Action
        data object Retry : Action
        data class OnMovieClick(val movieId: Int) : Action
    }

    sealed interface Event : UiEvent {
        data class NavigateToDetail(val movieId: Int) : Event
    }
}