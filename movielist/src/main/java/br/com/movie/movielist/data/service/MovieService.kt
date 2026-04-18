package br.com.movie.movielist.data.service

import br.com.movie.movielist.data.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {@GET("account/23020224/lists")
suspend fun getMyLists(
    @Query("page") page: Int = 1
): MovieListResponse
}