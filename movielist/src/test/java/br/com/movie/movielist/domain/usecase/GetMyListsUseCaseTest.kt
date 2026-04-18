package br.com.movie.movielist.domain.usecase

import app.cash.turbine.test
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.domain.model.Resource
import br.com.movie.movielist.domain.repository.MovieRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMyListsUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val useCase = GetMyListsUseCase(repository)

    @Test
    fun `when invoke is called then it should emit Loading and Success states`() = runBlocking {
        val movies = listOf(
            Movie(
                id = 1,
                name = "Gladiador",
                description = "Um herói romano",
                posterPath = "https://image.tmdb.org/t/p/w500/path.jpg"
            )
        )
        // Simulamos o repositório retornando um Flow de sucesso
        every { repository.getMyLists(1) } returns flowOf(movies)

        useCase(1).test {
            assertEquals(Resource.Loading, awaitItem())

            val successItem = awaitItem() as Resource.Success
            assertEquals(movies, successItem.data)
            assertEquals(1, successItem.data.size)
            assertEquals("Gladiador", successItem.data[0].name)

            awaitComplete()
        }
    }

    @Test
    fun `when invoke is called and repository fails then it should emit Loading and Error states`() = runBlocking {
        val exception = Exception("Erro de conexão")
        every { repository.getMyLists(1) } returns flow { throw exception }

        useCase(1).test {
            assertEquals(Resource.Loading, awaitItem())

            val errorItem = awaitItem() as Resource.Error
            assertEquals(exception, errorItem.exception)

            awaitComplete()
        }
    }
}