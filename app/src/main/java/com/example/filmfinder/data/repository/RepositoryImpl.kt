package com.example.filmfinder.data.repository

import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.getPopularMovies
import com.example.filmfinder.data.getUpcomingMovies

class RepositoryImpl : Repository {
    override fun getPopularFilmFromLocaleStorage() = getPopularMovies()

    override fun getUpcomingFilmFromLocaleStorage() = getUpcomingMovies()

    override fun getFilmFromServer() = Movie()

}