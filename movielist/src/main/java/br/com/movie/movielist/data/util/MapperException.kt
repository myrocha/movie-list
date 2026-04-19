package br.com.movie.movielist.data.util

import br.com.movie.movielist.domain.error.DataError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): DataError.Network {
    return when (this) {
        is IOException -> DataError.Network.NO_INTERNET
        is HttpException -> {
            when (this.code()) {
                in 500..599 -> DataError.Network.SERVICE_UNAVAILABLE
                else -> DataError.Network.CLIENT_ERROR
            }
        }
        else -> DataError.Network.UNKNOWN
    }
}