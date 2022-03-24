package com.example.filmfinder.data

import app.moviebase.tmdb.TmdbUrlParameter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListsApi {
    @GET("/3/movie/popular?")
    fun getPopularMovies(
        @Query(TmdbUrlParameter.API_KEY) token: String,
        @Query("language") language: String,
        @Query("page")page:Int
    ): Call<MovieList>

    @GET("/3/movie/upcoming?")
    fun getUpcomingMovies(
        @Query(TmdbUrlParameter.API_KEY) token: String,
        @Query("language") language: String,
        @Query("page")page:Int
    ): Call<MovieList>
}