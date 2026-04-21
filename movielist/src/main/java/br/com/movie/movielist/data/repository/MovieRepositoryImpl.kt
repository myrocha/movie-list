package br.com.movie.movielist.data.repository

import br.com.movie.movielist.data.mapper.MovieMapper
import br.com.movie.movielist.data.service.MovieService
import br.com.movie.movielist.data.util.toNetworkError
import br.com.movie.movielist.domain.error.DataError
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.domain.repository.MovieRepository
import br.com.movie.movielist.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.cancellation.CancellationException

class MovieRepositoryImpl(
    private val service: MovieService,
    private val mapper: MovieMapper,
    private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getMyLists(page: Int, language: String): Flow<Result<List<Movie>, DataError.Network>> =
        flow {
            try {
                val response = service.getMyLists(language, page)
                val domainMovies = response.results?.map { mapper.toDomain(it) } ?: emptyList()
                emit(Result.Success(domainMovies))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                emit(Result.Error(e.toNetworkError()))
            }
        }.flowOn(ioDispatcher)
}