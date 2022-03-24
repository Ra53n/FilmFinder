package com.example.filmfinder.data.repository

import com.example.filmfinder.BuildConfig
import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.MovieList
import com.example.filmfinder.data.MovieListsApi
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryImpl : Repository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(MovieListsApi::class.java)

    override fun getPopularFilmFromService(callback: Callback<MovieList>) {
        retrofit.getPopularMovies(BuildConfig.MOVIE_API_KEY,"ru",1).enqueue(callback)
    }

    override fun getUpcomingFilmFromService(callback: Callback<MovieList>) {
        retrofit.getUpcomingMovies(BuildConfig.MOVIE_API_KEY,"ru",1).enqueue(callback)
    }

    override fun getFilmFromServer() = Movie()

}