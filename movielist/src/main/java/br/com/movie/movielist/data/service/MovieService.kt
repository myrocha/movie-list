package br.com.movie.movielist.data.service

import br.com.movie.movielist.data.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getMyLists(
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieListResponse

}