package com.example.filmfinder.data.repository.localRepo

import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.room.App
import com.example.filmfinder.data.room.likedMovies.LikedMoviesEntity

class RepositoryLocalImpl : RepositoryLocal {
    override fun getAllLikedMovies(): List<Movie> {
        return App.getLikedMoviesDao().getAllLikedMovies()
            .map { convertLikedMovieToMovie(it) }
    }

    override fun saveMovie(movie: Movie) {
        App.getLikedMoviesDao().insert(convertMovieToLikedMovie(movie))
    }

    override fun deleteMovie(movie: Movie) {
        App.getLikedMoviesDao().delete(convertMovieToLikedMovie(movie))
    }

    private fun convertMovieToLikedMovie(movie: Movie) = with(movie) {
        LikedMoviesEntity(0, title, overview, realiseDate, rating, posterPath)
    }


    private fun convertLikedMovieToMovie(likedMoviesEntity: LikedMoviesEntity) =
        with(likedMoviesEntity) {
            Movie(id,title, overview, realiseDate, rating, posterPath)
        }
}