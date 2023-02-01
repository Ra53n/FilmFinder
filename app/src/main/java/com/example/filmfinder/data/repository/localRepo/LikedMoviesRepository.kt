package com.example.filmfinder.data.repository.localRepo

import com.example.filmfinder.domain.model.MovieEntity

interface LikedMoviesRepository {
    suspend fun getAllLikedMovies(): List<MovieEntity>
    suspend fun getLikedMoviesSortedByRating(isAsc: Boolean): List<MovieEntity>
    suspend fun likeMovie(movie: MovieEntity)
    suspend fun deleteMovieFromLikes(movie: MovieEntity)
}