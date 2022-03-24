package com.example.filmfinder.data

import app.moviebase.tmdb.TmdbUrlParameter.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsAPI {
    @GET("/3/movie/{movie_id}?")
    fun getMovie(
        @Path("movie_id") id: Long,
        @Query(API_KEY) token: String,
        @Query("language") language: String
    ): Call<MovieDTO>
}