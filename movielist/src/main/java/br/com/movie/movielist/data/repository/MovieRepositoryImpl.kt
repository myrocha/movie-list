package br.com.movie.movielist.data.repository

import br.com.movie.movielist.data.mapper.MovieMapper
import br.com.movie.movielist.data.service.MovieService
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(private val service: MovieService, private val mapper: MovieMapper) :
    MovieRepository {
    override fun getMyLists(page: Int): Flow<List<Movie>> = flow {
        val response = service.getMyLists(page)

        val domainMovies = response.results?.map {
            mapper.toDomain(it)
        } ?: emptyList()

        emit(domainMovies)
    }.flowOn(Dispatchers.IO)

}