package br.com.movie.movielist.data.util

import br.com.movie.movielist.domain.error.DataError
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): DataError.Network {
    return when (this) {
        is IOException -> DataError.Network.NO_INTERNET
        is JsonParseException -> DataError.Network.SERIALIZATION
        is HttpException -> when (this.code()) {
            500, 503 -> DataError.Network.SERVICE_UNAVAILABLE
            401 -> DataError.Network.UNAUTHORIZED
            else -> DataError.Network.UNKNOWN
        }
        else -> DataError.Network.UNKNOWN
    }
}
