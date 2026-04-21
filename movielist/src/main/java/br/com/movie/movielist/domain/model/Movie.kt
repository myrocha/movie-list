package br.com.movie.movielist.domain.model

data class Movie(
    val id: Int,
    val name: String,
    val description: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: String
)
