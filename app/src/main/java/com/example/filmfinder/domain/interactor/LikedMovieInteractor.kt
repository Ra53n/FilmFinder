package com.example.filmfinder.domain.interactor

import com.example.filmfinder.data.repository.localRepo.LikedMoviesRepository
import com.example.filmfinder.domain.model.MovieEntity

class LikedMovieInteractor(
    private val repo: LikedMoviesRepository
) {

    suspend fun getAllLikedMovies() = repo.getAllLikedMovies()

    suspend fun getLikedMoviesSortedByRating(isAsc: Boolean) = repo.getLikedMoviesSortedByRating(isAsc)

    suspend fun likeMovie(movie: MovieEntity) {
        repo.likeMovie(movie)
    }

    suspend fun deleteMovieFromLikes(movie: MovieEntity) {
        repo.deleteMovieFromLikes(movie)
    }
}