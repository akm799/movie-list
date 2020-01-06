package com.akm.test.movielist.data.api.service

import com.akm.test.movielist.data.api.model.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("3/movie/popular")
    fun getMovies(@Query("page") page: Int): Call<GetMoviesResponse>
}