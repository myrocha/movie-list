package br.com.movie.movielist.data.mapper

import br.com.movie.movielist.data.model.MovieListItemResponse
import br.com.movie.movielist.domain.model.Movie
import java.text.SimpleDateFormat
import java.util.Locale

interface MovieMapper {
    fun toDomain(response: MovieListItemResponse): Movie
}

private val apiFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
private val displayFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

private val lock = Any()

class MovieMapperImpl : MovieMapper {
    override fun toDomain(response: MovieListItemResponse): Movie {
        return Movie(
            id = response.id,
            name = response.title.orEmpty(),
            description = response.overview.orEmpty(),
            posterPath = response.posterPath?.let { path ->
                "https://image.tmdb.org/t/p/w500$path"
            }.orEmpty(),
            releaseDate = response.releaseDate.formatToDisplay(),
            voteAverage = response.voteAverage ?: 0.0
        )
    }
}

private fun String?.formatToDisplay(): String {
    if (this.isNullOrBlank()) return ""

    return try {
        synchronized(lock) {
            val date = apiFormatter.parse(this)
            date?.let { displayFormatter.format(it) } ?: ""
        }
    } catch (e: Exception) {
        ""
    }
}
