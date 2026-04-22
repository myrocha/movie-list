package br.com.movie.movielist.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.movie.movielist.R
import br.com.movie.movielist.domain.error.DataError

data class DataErrorVO(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val imageRes: Int
)

fun DataError.toVO(): DataErrorVO {
    return when (this) {
        DataError.Network.NO_INTERNET -> DataErrorVO(
            title = R.string.error_no_internet_title,
            description = R.string.error_no_internet_description,
            imageRes = R.drawable.icon_wifi_off
        )

        DataError.Network.SERVICE_UNAVAILABLE, DataError.Network.UNKNOWN, DataError.Network.SERIALIZATION, DataError.Network.UNAUTHORIZED -> DataErrorVO(
            title = R.string.error_generic_title,
            description = R.string.error_generic_description,
            imageRes = R.drawable.icon_alert_triangle
        )
    }
}