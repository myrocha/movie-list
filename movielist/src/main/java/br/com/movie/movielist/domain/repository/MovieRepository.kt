package br.com.movie.movielist.domain.repository

import br.com.movie.movielist.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMyLists(page: Int): Flow<List<Movie>>
}