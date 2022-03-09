package com.example.filmfinder.data

class RepositoryImpl : Repository {
    override fun getPopularFilmFromLocaleStorage(): List<Movie> {
        return getPopularMovies()
    }

    override fun getUpcomingFilmFromLocaleStorage(): List<Movie> {
        return getUpcomingMovies()
    }

    override fun getFilmFromServer(): Movie {
        return Movie()
    }
}