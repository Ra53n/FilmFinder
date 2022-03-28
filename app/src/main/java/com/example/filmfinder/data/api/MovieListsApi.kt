package com.example.filmfinder.data.api

import app.moviebase.tmdb.TmdbUrlParameter
import com.example.filmfinder.data.MovieListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListsApi {
    @GET("/3/discover/movie?")
    fun getPopularMovies(
        @Query(TmdbUrlParameter.API_KEY) token: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieListDTO>

    @GET("/3/movie/upcoming?")
    fun getUpcomingMovies(
        @Query(TmdbUrlParameter.API_KEY) token: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieListDTO>
}