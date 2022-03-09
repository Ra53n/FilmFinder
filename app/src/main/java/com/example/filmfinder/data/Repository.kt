package com.example.filmfinder.data

interface Repository {
    fun getFilmFromLocaleStorage(): Movie
    fun getFilmFromServer(): Movie
}