package com.example.filmfinder.data.repository

import com.example.filmfinder.data.Movie

interface Repository {
    fun getPopularFilmFromLocaleStorage(): List<Movie>
    fun getUpcomingFilmFromLocaleStorage(): List<Movie>
    fun getFilmFromServer(): Movie
}