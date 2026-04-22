package br.com.movie.movielist.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.R
import coil.compose.AsyncImage
import br.com.movie.movielist.iu.theme.color.amber
import br.com.movie.movielist.iu.theme.dimen.Dimens.iconSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.movieItemHeight
import br.com.movie.movielist.iu.theme.dimen.Dimens.paddingMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.paddingSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.radiusMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingExtraSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingExtraMedium

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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

            RatingBadge(rating = movie.voteAverage, modifier = Modifier.align(
                Alignment.TopEnd
            ))
        }

        Spacer(modifier = Modifier.height(spacingExtraMedium))

        Text(
            text = movie.name,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RatingBadge(rating: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color.Black.copy(alpha = 0.7f),
        shape = RoundedCornerShape(bottomStart = paddingMedium),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = paddingSmall, vertical = spacingExtraSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Star, contentDescription = null, tint = amber, modifier = Modifier.size(iconSmall))
            Spacer(modifier = Modifier.width(spacingExtraSmall))
            Text(text = rating, color = Color.White, style = MaterialTheme.typography.labelMedium)
        }
    }
}