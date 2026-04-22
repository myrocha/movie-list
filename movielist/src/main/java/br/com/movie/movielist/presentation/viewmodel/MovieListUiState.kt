package br.com.movie.movielist.presentation.viewmodel

import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.presentation.model.DataErrorVO

data class MovieListUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: DataErrorVO? = null
)