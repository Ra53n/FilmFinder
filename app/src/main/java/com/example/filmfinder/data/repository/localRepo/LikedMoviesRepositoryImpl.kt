package com.example.filmfinder.data.repository.localRepo

import com.example.filmfinder.data.mapper.LikedMovieModelMapper
import com.example.filmfinder.data.room.Database
import com.example.filmfinder.domain.model.MovieEntity

class LikedMoviesRepositoryImpl(
    private val database: Database,
    private val likedMovieMapper: LikedMovieModelMapper,
) : LikedMoviesRepository {
    override suspend fun getAllLikedMovies(): List<MovieEntity> {
        return database.likedMovieDao()
            .getAllLikedMovies()
            .map { likedMovieMapper.map(it) }
    }

    override suspend fun getLikedMoviesSortedByRating(isAsc: Boolean): List<MovieEntity> {
        return database.likedMovieDao()
            .getAllMoviesSortedByRating(getAscValueForDb(isAsc))
            .map { likedMovieMapper.map(it) }
    }

    override suspend fun likeMovie(movie: MovieEntity) {
        database.likedMovieDao().insert(likedMovieMapper.map(movie))
    }

    override suspend fun deleteMovieFromLikes(movie: MovieEntity) {
        database.likedMovieDao().delete(likedMovieMapper.map(movie))
    }

    private fun getAscValueForDb(isAsc: Boolean) =
        if (isAsc) {
            ASC
        } else {
            DESC
        }

    companion object {
        private const val ASC = 2
        private const val DESC = 1
    }
}