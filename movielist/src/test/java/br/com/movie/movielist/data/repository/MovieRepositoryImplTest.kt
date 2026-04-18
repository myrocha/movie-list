package br.com.movie.movielist.data.repository

import app.cash.turbine.test
import br.com.movie.movielist.data.mapper.MovieMapper
import br.com.movie.movielist.data.model.MovieListItemResponse
import br.com.movie.movielist.data.model.MovieListResponse
import br.com.movie.movielist.data.service.MovieService
import br.com.movie.movielist.domain.model.Movie
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MovieRepositoryImplTest {

    private val service: MovieService = mockk()
    private val mapper: MovieMapper = mockk()

    private val repository = MovieRepositoryImpl(service, mapper)

    @Test
    fun `when getMyLists is called then it should emit correctly mapped movie list`() =
        runBlocking {
            val movieDto = MovieListItemResponse(
                id = 1,
                name = "Gladiador",
                description = "Filmes de tirar o fôlego",
                posterPath = "/path.jpg",
                favoriteCount = 10,
                itemCount = 5,
                iso6391 = "pt",
                listType = "movie"
            )

            val mockResponse = MovieListResponse(
                page = 1,
                totalPages = 10,
                totalResults = 100,
                results = listOf(movieDto)
            )

            val expectedMovie = Movie(
                id = 1,
                name = "Gladiador",
                description = "Filmes de tirar o fôlego",
                posterPath = "https://image.tmdb.org/t/p/w500/path.jpg"
            )

            coEvery { service.getMyLists(any()) } returns mockResponse

            every { mapper.toDomain(movieDto) } returns expectedMovie

            repository.getMyLists(1).test {
                val movies = awaitItem()

                Assert.assertEquals(1, movies.size)
                Assert.assertEquals("Gladiador", movies[0].name)
                Assert.assertEquals(
                    "https://image.tmdb.org/t/p/w500/path.jpg",
                    movies[0].posterPath
                )
                awaitComplete()
            }
        }

    @Test
    fun `when getMyLists is called then it should emit empty mapped movie list`() = runBlocking {
        val mockResponse = MovieListResponse(
            page = 1,
            totalPages = 0,
            totalResults = 0,
            results = null
        )
        coEvery { service.getMyLists(any()) } returns mockResponse

        repository.getMyLists(1).test {
            val movies = awaitItem()
            Assert.assertEquals(0, movies.size)
            awaitComplete()
        }
    }
}