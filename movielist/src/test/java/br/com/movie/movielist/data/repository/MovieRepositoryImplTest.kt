package br.com.movie.movielist.data.repository

import app.cash.turbine.test
import br.com.movie.movielist.data.mapper.MovieMapper
import br.com.movie.movielist.data.model.MovieListResponse
import br.com.movie.movielist.data.service.MovieService
import br.com.movie.movielist.domain.error.DataError
import br.com.movie.movielist.domain.util.Result
import com.google.gson.JsonParseException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertFailsWith

class MovieRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private val service: MovieService = mockk()
    private val mapper: MovieMapper = mockk()

    private val repository = MovieRepositoryImpl(service, mapper, testDispatcher)

    @Test
    fun `when getMyLists returns success then it should emit Result Success`() = runTest(testDispatcher) {
        val mockResponse = MovieListResponse(
            page = 1,
            results = emptyList(),
            totalPages = 1,
            totalResults = 0
        )
        coEvery { service.getMyLists(any()) } returns mockResponse

        repository.getMyLists(1).test {
            val result = awaitItem()

            assertTrue(result is Result.Success)
            assertEquals(0, (result as Result.Success).data.size)
            awaitComplete()
        }
    }

    @Test
    fun `when api throws IOException then it should emit Result Error NO_INTERNET`() = runTest(testDispatcher) {
        coEvery { service.getMyLists(any()) } throws IOException()

        repository.getMyLists(1).test {
            val result = awaitItem()

            assertTrue(result is Result.Error)
            assertEquals(DataError.Network.NO_INTERNET, (result as Result.Error).error)
            awaitComplete()
        }
    }

    @Test
    fun `when api throws HttpException 500 then it should emit Result Error SERVICE_UNAVAILABLE`() = runTest(testDispatcher) {
        val errorResponse = Response.error<MovieListResponse>(
            500,
            "Internal Server Error".toResponseBody(null)
        )
        coEvery { service.getMyLists(any()) } throws HttpException(errorResponse)

        repository.getMyLists(1).test {
            val result = awaitItem()

            assertTrue(result is Result.Error)
            assertEquals(DataError.Network.SERVICE_UNAVAILABLE, (result as Result.Error).error)
            awaitComplete()
        }
    }

    @Test    fun `when api throws JsonParseException then it should emit Result Error SERIALIZATION`() = runTest(testDispatcher) {
        coEvery { service.getMyLists(any()) } throws JsonParseException("Malformed JSON")

        repository.getMyLists(1).test {
            val result = awaitItem()

            assertTrue(result is Result.Error)
            assertEquals(DataError.Network.SERIALIZATION, (result as Result.Error).error)
            awaitComplete()
        }
    }

    @Test
    fun `when api throws HttpException 401 then it should emit Result Error UNAUTHORIZED`() = runTest(testDispatcher) {
        val errorResponse = "{}".toResponseBody("application/json".toMediaTypeOrNull())
        val exception = HttpException(Response.error<Any>(401, errorResponse))

        coEvery { service.getMyLists(any()) } throws exception

        repository.getMyLists(1).test {
            val result = awaitItem()

            assertTrue(result is Result.Error)
            assertEquals(DataError.Network.UNAUTHORIZED, (result as Result.Error).error)
            awaitComplete()
        }
    }

    @Test
    fun `when api throws CancellationException then it should rethrow the exception`() = runTest(testDispatcher) {
        val exception = CancellationException("Job was cancelled")
        coEvery { service.getMyLists(any()) } throws exception
        assertFailsWith<CancellationException> {
            repository.getMyLists(1).collect()
        }
    }
}