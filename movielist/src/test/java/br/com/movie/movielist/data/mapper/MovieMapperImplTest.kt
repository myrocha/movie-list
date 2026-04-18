package br.com.movie.movielist.data.mapper

import br.com.movie.movielist.data.model.MovieListItemResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieMapperImplTest {

    private val mapper = MovieMapperImpl()

    @Test
    fun `when mapper receives a valid dto then it should return a domain movie with full image url`() {
        val dto = MovieListItemResponse(
            id = 1,
            name = "Gladiador",
            description = "Descrição",
            posterPath = "/path.jpg",
            favoriteCount = 0,
            itemCount = 0,
            iso6391 = "pt",
            listType = "movie"
        )

        val result = mapper.toDomain(dto)

        val expectedUrl = "https://image.tmdb.org/t/p/w500/path.jpg"
        assertEquals(1, result.id)
        assertEquals("Gladiador", result.name)
        assertEquals(expectedUrl, result.posterPath)
    }

    @Test
    fun `when mapper receives a null posterPath then it should return an empty string for image url`() {
        val dto = MovieListItemResponse(
            id = 2,
            name = "Filme Sem Foto",
            description = null,
            posterPath = null,
            favoriteCount = 0,
            itemCount = 0,
            iso6391 = "en",
            listType = "movie"
        )

        val result = mapper.toDomain(dto)

        assertEquals("", result.posterPath)
    }

    @Test
    fun `when mapper receives null fields then it should return default values`() {
        val dto = MovieListItemResponse(
            id = 0,
            name = null,
            description = null,
            posterPath = null,
            favoriteCount = null,
            itemCount = null,
            iso6391 = null,
            listType = null
        )

        val result = mapper.toDomain(dto)

        assertEquals(0, result.id)
        assertEquals("", result.name)
        assertEquals("", result.description)
        assertEquals("", result.posterPath)
    }
}