package br.com.movie.movielist.domain.usecase

import br.com.movie.movielist.domain.error.DataError
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.domain.repository.MovieRepository
import br.com.movie.movielist.domain.util.Result
import kotlinx.coroutines.flow.Flow

class GetMyListsUseCase(private val repository: MovieRepository) {
    operator fun invoke(
        page: Int? = null,
        language: String? = null
    ): Flow<Result<List<Movie>, DataError.Network>> {
        val validatedPage = if (page != null && page > 1) page else 1
        val validatedLanguage = language ?: "en-US"
        return repository.getMyLists(validatedPage, validatedLanguage)
    }
}