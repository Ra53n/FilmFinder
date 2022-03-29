package com.example.filmfinder.data.repository

import com.example.filmfinder.data.Movie

interface RepositoryLocal {
    fun getAllLikedMovies():List<Movie>
    fun saveMovie(movie: Movie)
    fun deleteMovie(movie: Movie)
}