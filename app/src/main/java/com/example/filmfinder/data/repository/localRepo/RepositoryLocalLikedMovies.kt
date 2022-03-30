package com.example.filmfinder.data.repository.localRepo

import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.room.likedMovies.LikedMoviesEntity

interface RepositoryLocalLikedMovies {
    fun getAllLikedMovies():List<Movie>
    fun saveMovie(movie: Movie)
    fun deleteMovie(movie: Movie)
    fun deleteMovie(entity: LikedMoviesEntity)
}