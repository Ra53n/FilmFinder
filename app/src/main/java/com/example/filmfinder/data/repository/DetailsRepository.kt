package com.example.filmfinder.data.repository

import com.example.filmfinder.data.MovieDTO
import retrofit2.Callback

interface DetailsRepository {
    fun getMovieFromServer(id:Int,callback: Callback<MovieDTO>)
}