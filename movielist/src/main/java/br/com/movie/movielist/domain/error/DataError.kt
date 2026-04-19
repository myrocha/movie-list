package br.com.movie.movielist.domain.error

sealed interface DataError {
    enum class Network : DataError {
        SERVICE_UNAVAILABLE,
        CLIENT_ERROR,
        NO_INTERNET,
        SERIALIZATION,
        UNKNOWN
    }
}
