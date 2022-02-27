package com.example.filmfinder.data

class RepositoryImpl : Repository {
    override fun getFilmFromLocaleStorage(): Movie {
        return Movie()
    }

    override fun getFilmFromServer(): Movie {
        return Movie()
    }
}