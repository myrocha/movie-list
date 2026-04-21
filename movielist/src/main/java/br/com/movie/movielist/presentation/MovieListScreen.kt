package br.com.movie.movielist.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.iu.theme.color.PurpleDark
import br.com.movie.movielist.iu.theme.color.PurpleMedium
import br.com.movie.movielist.R
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.geometry.Offset
import br.com.movie.movielist.iu.theme.dimen.Dimens.iconSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.movieItemHeight
import br.com.movie.movielist.iu.theme.dimen.Dimens.paddingMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.paddingSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.radiusMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.radiusSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.shimmerTitleHeight
import br.com.movie.movielist.iu.theme.dimen.Dimens.sizeExtraSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingExtraSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingLarge
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingExtraMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingNormal
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingSmall

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
                modifier = Modifier.padding(start = paddingMedium, top = paddingMedium)
            )

            Spacer(modifier = Modifier.height(sizeExtraSmall))

            Text(
                text = "Descubra os filmes mais assistidos",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = paddingMedium)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(paddingMedium),
                horizontalArrangement = Arrangement.spacedBy(spacingMedium),
                verticalArrangement = Arrangement.spacedBy(spacingLarge),
                modifier = Modifier.fillMaxSize()
            ) {
                if (uiState.isLoading) {
                    items(6) {
                        MovieItemShimmer()
                    }
                } else {
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(movieItemHeight)
                .clip(RoundedCornerShape(radiusMedium))
        ) {
            AsyncImage(
                model = coil.request.ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                    .data(movie.posterPath)
                    .crossfade(true)
                    .crossfade(400)
                    .build(),
                contentDescription = movie.name,
                placeholder = painterResource(id = R.drawable.img),
                error = painterResource(id = R.drawable.img),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Surface(
                color = Color.Black.copy(alpha = 0.7f),
                shape = RoundedCornerShape(bottomStart = paddingMedium),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = paddingSmall, vertical = spacingExtraSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(iconSmall)
                    )
                    Spacer(modifier = Modifier.width(spacingExtraSmall))
                    Text(
                        text = String.format("%.1f", movie.voteAverage),
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(spacingExtraMedium))

        Text(
            text = movie.name,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(spacingExtraSmall))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(iconSmall)
            )

            Spacer(modifier = Modifier.width(spacingSmall))

            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun MovieItemShimmer() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(movieItemHeight)
                .clip(RoundedCornerShape(radiusMedium))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(spacingExtraMedium))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(shimmerTitleHeight)
                .clip(RoundedCornerShape(radiusSmall))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(spacingNormal))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(spacingExtraMedium)
                .clip(RoundedCornerShape(radiusSmall))
                .shimmerEffect()
        )
    }
}

@Composable
fun Modifier.shimmerEffect(): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    return this.background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.1f),
                Color.White.copy(alpha = 0.3f),
                Color.White.copy(alpha = 0.1f),
            ),
            start = Offset(translateAnim, translateAnim),
            end = Offset(translateAnim + 500f, translateAnim + 500f)
        )
    )
}