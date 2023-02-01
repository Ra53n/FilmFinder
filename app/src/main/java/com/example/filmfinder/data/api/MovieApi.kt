package com.example.filmfinder.data.api

import com.example.filmfinder.data.api.response.MovieItem
import com.example.filmfinder.data.api.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("/API/MostPopularMovies/")
    suspend fun getPopularMovies(
        @Query("apiKey") apiKey: String,
        @Query("lang") lang: String
    ): MovieResponse

    @GET("/API/ComingSoon/")
    suspend fun getUpcoming(
        @Query("apiKey") apiKey: String,
        @Query("lang") lang: String
    ): MovieResponse

    @GET("/{lang}/API/Title/{apiKey}/{id}/")
    suspend fun getById(
        @Path("apiKey") apiKey: String,
        @Path("id") id: String,
        @Path("lang") lang: String
    ): MovieItem
}