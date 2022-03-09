package com.example.filmfinder.data

interface Repository {
    fun getPopularFilmFromLocaleStorage(): List<Movie>
    fun getUpcomingFilmFromLocaleStorage(): List<Movie>
    fun getFilmFromServer(): Movie
}