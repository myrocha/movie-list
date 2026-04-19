package br.com.movie.movielist.domain.util

import br.com.movie.movielist.domain.error.DataError

sealed interface Result<out D, out E: DataError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: DataError>(val error: E): Result<Nothing, E>
}