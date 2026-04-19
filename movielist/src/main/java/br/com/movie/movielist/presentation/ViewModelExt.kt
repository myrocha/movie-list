package br.com.movie.movielist.presentation

import br.com.movie.movielist.domain.util.Result
import br.com.movie.movielist.domain.error.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

fun <T, E : DataError> Flow<Result<T, E>>.handleStates(
    state: MutableStateFlow<MovieListUiState>,
    onSuccess: (T) -> Unit
): Flow<Result<T, E>> = onStart {
    state.update { it.copy(isLoading = true, errorMessage = null) }
}.onEach { result ->
    when (result) {
        is Result.Success -> {
            state.update { it.copy(isLoading = false, errorMessage = null) }
            onSuccess(result.data)
        }
        is Result.Error -> {
            state.update { it.copy(isLoading = false, errorMessage = result.error.toString()) }
        }
    }
}