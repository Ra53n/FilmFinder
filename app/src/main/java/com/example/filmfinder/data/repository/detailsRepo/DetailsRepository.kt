package com.example.filmfinder.data.repository.detailsRepo

import com.example.filmfinder.data.MovieDTO
import retrofit2.Callback

interface DetailsRepository {
    fun getMovieFromServer(id: Long, callback: Callback<MovieDTO>)
}