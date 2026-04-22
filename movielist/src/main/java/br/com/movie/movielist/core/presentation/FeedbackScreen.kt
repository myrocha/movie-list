package br.com.movie.movielist.core.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.com.movie.movielist.iu.theme.color.feedbackCardBackground
import br.com.movie.movielist.iu.theme.color.feedbackPrimaryButton
import br.com.movie.movielist.iu.theme.color.feedbackSecondaryButton
import br.com.movie.movielist.iu.theme.color.purpleDark
import br.com.movie.movielist.iu.theme.color.purpleMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens
import br.com.movie.movielist.iu.theme.dimen.Dimens.feedbackButtonHeight
import br.com.movie.movielist.iu.theme.dimen.Dimens.feedbackIconSize
import br.com.movie.movielist.iu.theme.dimen.Dimens.fontSizeButton
import br.com.movie.movielist.iu.theme.dimen.Dimens.radiusMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingExtraLarge
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingExtraMedium
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingHuge
import br.com.movie.movielist.iu.theme.dimen.Dimens.spacingNormal

@Composable
fun FeedbackScreen(
    title: String,
    description: String,
    @DrawableRes imageRes: Int,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(purpleMedium, purpleDark)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(
                    color = feedbackCardBackground,
                    shape = RoundedCornerShape(Dimens.radiusExtraLarge)
                )
                .padding(horizontal = Dimens.paddingLarge, vertical = spacingHuge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(Dimens.feedbackImageSize)
            )
            Spacer(modifier = Modifier.height(spacingExtraLarge))

            Text(
                text = title,
                color = Color.White,
                fontSize = Dimens.fontSizeTitle,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = description,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = Dimens.fontSizeMedium,
                lineHeight = Dimens.fontSizeTitle,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(spacingHuge))

            Button(
                onClick = onPrimaryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(feedbackButtonHeight),
                colors = ButtonDefaults.buttonColors(
                    containerColor = feedbackPrimaryButton
                ),
                shape = RoundedCornerShape(radiusMedium)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(feedbackIconSize)
                    )
                    Spacer(modifier = Modifier.width(spacingNormal))
                    Text(
                        text = primaryButtonText,
                        fontSize = fontSizeButton,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            if (secondaryButtonText != null && onSecondaryClick != null) {
                Spacer(modifier = Modifier.height(spacingExtraMedium))
                Button(
                    onClick = onSecondaryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(feedbackButtonHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = feedbackSecondaryButton
                    ),
                    shape = RoundedCornerShape(radiusMedium)
                ) {
                    Text(
                        text = secondaryButtonText,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}