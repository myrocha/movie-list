package br.com.movie.movielist.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.iu.theme.PurpleDark
import br.com.movie.movielist.iu.theme.PurpleMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable//transforma dados em interface
fun MovieListScreen(
    viewModel: MovieListViewModel = koinViewModel(),
    onNavigateToDetail: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    //Carrega a lista de filmes, só faz isso quando a tela nasce, como se fosse o oncreate
    LaunchedEffect(Unit) {
        viewModel.onAction(MovieListViewModel.Action.LoadMovies)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PurpleMedium, PurpleDark)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = "Filmes Populares",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Descubra os filmes mais assistidos",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )

            if (uiState.isLoading) {

            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxSize()

                ) {
                    items(uiState.movies) { movie ->
                        MovieItem(movie)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        coil.compose.AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.description,
            modifier = Modifier
                .fillMaxSize()
                .height(220.dp)
                .clip(
                    androidx.compose.foundation.shape.RoundedCornerShape(
                        8.dp
                    )
                ),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movie.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}