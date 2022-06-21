package com.example.filmfinder.data.repository.localRepo

import com.example.filmfinder.App
import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.room.likedMovies.LikedMoviesEntity
import com.example.filmfinder.data.room.movieNotes.MovieNotesEntity

class RepositoryLocalImpl : RepositoryLocalLikedMovies, RepositoryLocalNotes {
    override fun getAllLikedMovies(): List<Movie> {
        return App.getLikedMoviesDao().getAllLikedMovies()
            .map { convertLikedMovieToMovie(it) }
    }

    override fun getLikedMoviesSortedByRating(isAsc:Int): List<Movie> {
        return App.getLikedMoviesDao().getAllMoviesSortedByRating(isAsc)
            .map { convertLikedMovieToMovie(it) }
    }

    override fun saveMovie(movie: Movie) {
        App.getLikedMoviesDao().insert(convertMovieToLikedMovie(movie))
    }

    override fun deleteMovie(movie: Movie) {
        App.getLikedMoviesDao().delete(convertMovieToLikedMovie(movie))
    }

    override fun deleteMovie(entity: LikedMoviesEntity) {
        App.getLikedMoviesDao().delete(entity)
    }

    private fun convertMovieToLikedMovie(movie: Movie) = with(movie) {
        LikedMoviesEntity(id, title, overview, realiseDate, rating, posterPath)
    }


    private fun convertLikedMovieToMovie(likedMoviesEntity: LikedMoviesEntity) =
        with(likedMoviesEntity) {
            Movie(movieId, title, overview, realiseDate, rating, posterPath)
        }

    override fun getAllNotes(): List<MovieNotesEntity> {
        return App.getMovieNotesDao().getAllMovieNotes()
    }

    override fun addNote(entity: MovieNotesEntity) {
        App.getMovieNotesDao().insert(entity)
    }

    override fun deleteNote(entity: MovieNotesEntity) {
        App.getMovieNotesDao().delete(entity)
    }
}