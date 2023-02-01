package com.example.filmfinder.data.repository.remote

import com.example.filmfinder.BuildConfig
import com.example.filmfinder.data.api.MovieApi
import com.example.filmfinder.data.mapper.MovieResponseToEntityMapper
import com.example.filmfinder.domain.model.MovieEntity

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val mapper: MovieResponseToEntityMapper
) : MovieRepository {
    override suspend fun getPopularMovies(containsAdults: Boolean): List<MovieEntity> {
        val result = mapper.map(api.getPopularMovies(
            BuildConfig.MOVIE_API_KEY, "ru"
        ))
        return when {
            containsAdults -> {
                result.filter { it.contentRating.equals(other = "r", ignoreCase = true) }
            }
            else -> {
                result
            }
        }
    }


    override suspend fun getComingMovies(containsAdults: Boolean): List<MovieEntity> {
        val result = mapper.map(api.getUpcoming(
            BuildConfig.MOVIE_API_KEY, "ru"
        ))
        return when {
            containsAdults -> {
                result.filter { it.contentRating.equals(other = "r", ignoreCase = true) }
            }
            else -> {
                result
            }
        }
    }

    override suspend fun getMovieById(id: String): MovieEntity {
        return mapper.map(api.getById(BuildConfig.MOVIE_API_KEY, id, "ru"))
    }
}