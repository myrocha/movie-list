package br.com.movie.movielist.presentation.components


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import br.com.movie.movielist.iu.theme.dimen.Dimens.movieItemHeight
import br.com.movie.movielist.iu.theme.dimen.Dimens.radiusMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.radiusSmall
import br.com.movie.movielist.iu.theme.dimen.Dimens.shimmerTitleHeight
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingExtraMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingNormal

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
                .fillMaxWidth(0.8f)
                .height(shimmerTitleHeight)
                .clip(RoundedCornerShape(radiusSmall))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(spacingNormal))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(spacingExtraMedium)
                .clip(RoundedCornerShape(radiusSmall))
                .shimmerEffect()
        )
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
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

    this.background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.1f),
                Color.White.copy(alpha = 0.25f),
                Color.White.copy(alpha = 0.1f),
            ),
            start = Offset(translateAnim, translateAnim),
            end = Offset(translateAnim + 500f, translateAnim + 500f)
        )
    )
}