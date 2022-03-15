package com.example.filmfinder.data

class RepositoryImpl : Repository {
    override fun getPopularFilmFromLocaleStorage() = getPopularMovies()

    override fun getUpcomingFilmFromLocaleStorage() = getUpcomingMovies()

    override fun getFilmFromServer() = Movie()

}