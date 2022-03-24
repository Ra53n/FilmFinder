package com.example.filmfinder.data.repository

import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.MovieList
import retrofit2.Callback

interface Repository {
    fun getPopularFilmFromService(callback:Callback<MovieList>)
    fun getUpcomingFilmFromService(callback:Callback<MovieList>)
    fun getFilmFromServer(): Movie
}