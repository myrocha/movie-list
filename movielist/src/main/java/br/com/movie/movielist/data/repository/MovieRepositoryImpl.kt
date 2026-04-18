package br.com.movie.movielist.data.repository

import br.com.movie.movielist.data.service.MovieService
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(private val service: MovieService) : MovieRepository {
    override fun getMyLists(page: Int): Flow<List<Movie>> = flow{
        val response = service.getMyLists(page)

        val domainMovies = response.results?.map { dto ->
            Movie(
                id = dto.id ?: 0,
                name = dto.name.orEmpty(),
                description = dto.description.orEmpty(),
                posterPath = "https://image.tmdb.org/t/p/w500${dto.posterPath}"
            )
        } ?: emptyList()

        emit(domainMovies)
    }.flowOn(Dispatchers.IO)

}