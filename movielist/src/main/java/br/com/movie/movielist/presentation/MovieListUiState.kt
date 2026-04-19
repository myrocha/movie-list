package br.com.movie.movielist.presentation

import br.com.movie.movielist.domain.model.Movie

data class MovieListUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val errorMessage: String? = null
)