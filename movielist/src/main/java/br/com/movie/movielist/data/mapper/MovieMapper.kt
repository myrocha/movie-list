package br.com.movie.movielist.data.mapper

import br.com.movie.movielist.data.model.MovieListItemResponse
import br.com.movie.movielist.domain.model.Movie

interface MovieMapper {
    fun toDomain(response: MovieListItemResponse): Movie
}

class MovieMapperImpl : MovieMapper {
    override fun toDomain(response: MovieListItemResponse): Movie {
        return Movie(
            id = response.id,
            name = response.title.orEmpty(),
            description = response.overview.orEmpty(),
            posterPath = response.posterPath?.let { path ->
                "https://image.tmdb.org/t/p/w500$path"
            }.orEmpty()
        )
    }
}