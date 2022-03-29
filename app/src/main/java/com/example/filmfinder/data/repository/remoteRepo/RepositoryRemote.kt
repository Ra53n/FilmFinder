package com.example.filmfinder.data.repository.remoteRepo

import com.example.filmfinder.data.MovieListDTO
import retrofit2.Callback

interface RepositoryRemote {
    fun getPopularFilmFromService(adult:Boolean, callback: Callback<MovieListDTO>)
    fun getUpcomingFilmFromService(callback: Callback<MovieListDTO>)
}