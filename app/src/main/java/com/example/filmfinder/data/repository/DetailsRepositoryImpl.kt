package com.example.filmfinder.data.repository

import com.example.filmfinder.BuildConfig
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.data.MovieDetailsAPI
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsRepositoryImpl : DetailsRepository {
    override fun getMovieFromServer(id: Int,callback:Callback<MovieDTO>) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(MovieDetailsAPI::class.java)
        retrofit.getMovie(id,BuildConfig.MOVIE_API_KEY,"ru").enqueue(callback)
    }
}