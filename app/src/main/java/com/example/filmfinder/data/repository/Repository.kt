package com.example.filmfinder.data.repository

import com.example.filmfinder.data.MovieListDTO
import retrofit2.Callback

interface Repository {
    fun getPopularFilmFromService(callback: Callback<MovieListDTO>)
    fun getUpcomingFilmFromService(callback: Callback<MovieListDTO>)
}