package br.com.movie.movielist.data.mapper

import br.com.movie.movielist.data.model.MovieListItemResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieMapperImplTest {

    private val mapper = MovieMapperImpl()

    @Test
    fun `when mapper receives a valid dto then it should return a domain movie with full image url`() {
        val dto = MovieListItemResponse(
            id = 1226863,
            title = "The Super Mario Galaxy Movie",
            originalTitle = "The Super Mario Galaxy Movie",
            overview = "Having thwarted Bowser's previous plot...",
            posterPath = "/eJGWx219ZcEMVQJhAgMiqo8tYY.jpg",
            backdropPath = "/9Z2uDYXqJrlmePznQQJhL6d92Rq.jpg",
            releaseDate = "2026-04-01",
            voteAverage = 6.8,
            voteCount = 506,
            popularity = 780.1539,
            adult = false,
            video = false,
            originalLanguage = "en",
            genreIds = listOf(10751, 35, 12, 14, 16)
        )

        val result = mapper.toDomain(dto)

        val expectedUrl = "https://image.tmdb.org/t/p/w500/eJGWx219ZcEMVQJhAgMiqo8tYY.jpg"

        assertEquals(1226863, result.id)
        assertEquals("The Super Mario Galaxy Movie", result.name)
        assertEquals(expectedUrl, result.posterPath)
    }

    @Test
    fun `when mapper receives a null posterPath then it should return an empty string for image url`() {
        val dto = MovieListItemResponse(
            id = 2,
            title = "Filme Sem Foto",
            originalTitle = null,
            overview = null,
            posterPath = null,
            backdropPath = null,
            releaseDate = null,
            voteAverage = null,
            voteCount = null,
            popularity = null,
            adult = null,
            video = null,
            originalLanguage = null,
            genreIds = null
        )

        val result = mapper.toDomain(dto)

        assertEquals("", result.posterPath)
    }

    @Test
    fun `when mapper receives null fields then it should return default values`() {
        val dto = MovieListItemResponse(
            id = 0,
            title = null,
            originalTitle = null,
            overview = null,
            posterPath = null,
            backdropPath = null,
            releaseDate = null,
            voteAverage = null,
            voteCount = null,
            popularity = null,
            adult = null,
            video = null,
            originalLanguage = null,
            genreIds = null
        )

        val result = mapper.toDomain(dto)

        assertEquals(0, result.id)
        assertEquals("", result.name)
        assertEquals("", result.posterPath)
    }
}