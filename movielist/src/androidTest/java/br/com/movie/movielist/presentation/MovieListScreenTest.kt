package br.com.movie.movielist.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.movie.movielist.domain.model.Movie
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MovieListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_list_is_loaded_successfully_should_display_movie_name() {
        val movieName = "Titanic"
        val mockMovie = mockk<Movie>(relaxed = true)

        every { mockMovie.name } returns movieName
        val mockMovies = listOf(mockMovie)

        composeTestRule.setContent {
            MovieListScreenContent(
                uiState = MovieListUiState(
                    movies = mockMovies,
                    isLoading = false
                ),
                onAction = {}
            )
        }

        composeTestRule.onNodeWithText(movieName).assertIsDisplayed()

    }

    @Test
    fun when_movie_is_clicked_should_trigger_OnMovieClick_action_with_correct_id() {
        val movieId = 1912
        val movieName = "Titanic"
        var capturedMovieId = -1

        val mockMovie = mockk<Movie>(relaxed = true)
        every { mockMovie.id } returns movieId
        every { mockMovie.name } returns movieName

        composeTestRule.setContent {
            MovieListScreenContent(
                uiState = MovieListUiState(
                    movies = listOf(mockMovie),
                    isLoading = false
                ),
                onAction = { action ->
                    if (action is MovieListViewModel.Action.OnMovieClick) {
                        capturedMovieId = action.movieId
                    }
                }
            )
        }

        composeTestRule.onNodeWithText(movieName).performClick()

        Assert.assertEquals(movieId, capturedMovieId)
    }

    @Test
    fun when_state_is_loading_should_not_display_movie_list() {
        val movieName = "Titanic"
        val mockMovie = mockk<Movie>(relaxed = true)
        every { mockMovie.name } returns movieName

        composeTestRule.setContent {
            MovieListScreenContent(
                uiState = MovieListUiState(
                    movies = listOf(mockMovie),
                    isLoading = true
                ),
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithText(movieName)
            .assertDoesNotExist()
    }

}