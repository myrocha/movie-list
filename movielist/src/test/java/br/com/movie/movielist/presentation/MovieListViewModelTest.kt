package br.com.movie.movielist.presentation

import app.cash.turbine.test
import br.com.movie.movielist.domain.error.DataError
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.domain.usecase.GetMyListsUseCase
import br.com.movie.movielist.domain.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import br.com.movie.movielist.domain.util.Result.Error

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    private val getMyListsUseCase: GetMyListsUseCase = mockk()
    private lateinit var viewModel: MovieListViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieListViewModel(getMyListsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when LoadMovies action is sent, should update state with movies list`() = runTest {
        val mockMovies = listOf(mockk<Movie>(relaxed = true))
        val successResult = Result.Success(mockMovies)

        coEvery { getMyListsUseCase(page = 1, language = "en-US") } returns flowOf(successResult)

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(emptyList<Movie>(), initialState.movies)

            viewModel.onAction(MovieListViewModel.Action.LoadMovies)

            val successState = awaitItem()
            assertEquals(mockMovies, successState.movies)
            assertEquals(false, successState.isLoading)

            expectNoEvents()
        }
    }

    @Test
    fun `when LoadMovies fails, should update state with error and stop loading`() = runTest {
        val errorResult = Error(DataError.Network.NO_INTERNET)

        coEvery {
            getMyListsUseCase(page = 1, language = "en-US")
        } returns flowOf(errorResult)

        viewModel.uiState.test {
            awaitItem()

            viewModel.onAction(MovieListViewModel.Action.LoadMovies)

            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertTrue(errorState.movies.isEmpty())
        }
    }

    @Test
    fun `when OnMovieClick action is sent, should emit NavigateToDetail event`() = runTest {
        val movieId = 123

        viewModel.event.test {
            viewModel.onAction(MovieListViewModel.Action.OnMovieClick(movieId))

            val event = awaitItem()
            assertTrue(event is MovieListViewModel.Event.NavigateToDetail)
            assertEquals(movieId, (event as MovieListViewModel.Event.NavigateToDetail).movieId)
        }
    }
}
