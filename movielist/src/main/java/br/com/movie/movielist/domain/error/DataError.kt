package br.com.movie.movielist.domain.error

sealed interface DataError {
    enum class Network : DataError {
        SERVICE_UNAVAILABLE,
        NO_INTERNET,
        SERIALIZATION,
        UNAUTHORIZED,
        UNKNOWN
    }
}
