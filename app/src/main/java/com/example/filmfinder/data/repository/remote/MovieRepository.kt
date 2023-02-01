package com.example.filmfinder.data.repository.remote

import com.example.filmfinder.domain.model.MovieEntity

interface MovieRepository {

    suspend fun getPopularMovies(containsAdults: Boolean): List<MovieEntity>

    suspend fun getComingMovies(containsAdults: Boolean): List<MovieEntity>

    suspend fun getMovieById(id: String): MovieEntity
}