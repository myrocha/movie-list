package br.com.movie.movielist.domain.usecase

import app.cash.turbine.test
import br.com.movie.movielist.domain.error.DataError
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.domain.repository.MovieRepository
import br.com.movie.movielist.domain.util.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetMyListsUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val useCase = GetMyListsUseCase(repository)

    @Test
    fun `when invoke is called then it should return success result from repository`() = runTest {
        val movies = listOf(mockk<Movie>())
        every { repository.getMyLists(1) } returns flowOf(Result.Success(movies))

        useCase(1).test {
            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertEquals(movies, (result as Result.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `when repository returns error then usecase should emit the same error`() = runTest {
        val networkError = DataError.Network.SERVICE_UNAVAILABLE
        every { repository.getMyLists(1) } returns flowOf(Result.Error(networkError))

        useCase(1).test {
            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertEquals(networkError, (result as Result.Error).error)
            awaitComplete()
        }
    }
}