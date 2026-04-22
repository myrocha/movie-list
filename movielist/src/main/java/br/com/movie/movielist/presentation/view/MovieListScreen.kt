package br.com.movie.movielist.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Brush
import br.com.movie.movielist.iu.theme.color.purpleDark
import br.com.movie.movielist.iu.theme.color.purpleMedium
import br.com.movie.movielist.R
import androidx.compose.ui.res.stringResource
import br.com.movie.movielist.iu.theme.dimen.Dimens.paddingMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingLarge
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingMedium
import br.com.movie.movielist.core.presentation.FeedbackScreen
import br.com.movie.movielist.presentation.components.MovieHeader
import br.com.movie.movielist.presentation.components.MovieItem
import br.com.movie.movielist.presentation.components.MovieItemShimmer
import br.com.movie.movielist.presentation.viewmodel.MovieListUiState
import br.com.movie.movielist.presentation.viewmodel.MovieListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = koinViewModel(),
    onNavigateToDetail: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onAction(MovieListViewModel.Action.LoadMovies)
    }

    MovieListScreenContent(
        uiState = uiState,
        onAction = { action ->
            if (action is MovieListViewModel.Action.OnMovieClick) {
                onNavigateToDetail(action.movieId)
            } else {
                viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun MovieListScreenContent(
    uiState: MovieListUiState,
    onAction: (MovieListViewModel.Action) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(purpleMedium, purpleDark)
                )
            )
    ) {

        when {
            uiState.error != null -> {
                FeedbackScreen(
                    title = stringResource(uiState.error.title),
                    description = stringResource(uiState.error.description),
                    imageRes = uiState.error.imageRes,
                    primaryButtonText = stringResource(id = R.string.btn_retry),
                    onPrimaryClick = { onAction(MovieListViewModel.Action.LoadMovies) },
                    secondaryButtonText = stringResource(id = R.string.btn_back_home),
                    onSecondaryClick = { }
                )
            }

            !uiState.isLoading && uiState.movies.isEmpty() -> {
                FeedbackScreen(
                    title = stringResource(id = R.string.empty_list_title),
                    description = stringResource(id = R.string.empty_list_description),
                    imageRes = R.drawable.icon_film_empty,
                    primaryButtonText = stringResource(id = R.string.btn_explore),
                    onPrimaryClick = { onAction(MovieListViewModel.Action.LoadMovies) }
                )
            }

            else -> {
                MovieListGridContent(
                    uiState = uiState,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun MovieListGridContent(
    uiState: MovieListUiState,
    onAction: (MovieListViewModel.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        MovieHeader()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(paddingMedium),
            horizontalArrangement = Arrangement.spacedBy(spacingMedium),
            verticalArrangement = Arrangement.spacedBy(spacingLarge),
            modifier = Modifier.weight(1f)
        ) {
            if (uiState.isLoading) {
                items(6) { MovieItemShimmer() }
            } else {
                items(uiState.movies) { movie ->
                    MovieItem(
                        movie = movie,
                        onClick = { onAction(MovieListViewModel.Action.OnMovieClick(movie.id)) }
                    )
                }
            }
        }
    }
}
