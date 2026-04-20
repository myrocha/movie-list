package br.com.movie.movielist.domain.repository

import br.com.movie.movielist.domain.error.DataError
import br.com.movie.movielist.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import br.com.movie.movielist.domain.util.Result

interface MovieRepository {
    fun getMyLists(page: Int, language: String): Flow<Result<List<Movie>, DataError.Network>>
}