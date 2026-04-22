package br.com.movie.movielist.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import br.com.movie.movielist.R
import br.com.movie.movielist.iu.theme.dimen.Dimens.paddingMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.sizeExtraSmall

@Composable
fun MovieHeader() {
    Column {
        Text(
            text = stringResource(id = R.string.popular_movies_title),
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = paddingMedium, top = paddingMedium)
        )

        Spacer(modifier = Modifier.height(sizeExtraSmall))

        Text(
            text = stringResource(id = R.string.popular_movies_subtitle),
            color = Color.White.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = paddingMedium)
        )
    }
}