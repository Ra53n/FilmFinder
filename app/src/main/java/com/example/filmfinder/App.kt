package com.example.filmfinder

import android.app.Application
import androidx.room.Room
import com.example.filmfinder.data.room.likedMovies.LikedMoviesDao
import com.example.filmfinder.data.room.likedMovies.LikedMoviesDatabase
import com.example.filmfinder.data.room.movieNotes.MovieNotesDao
import com.example.filmfinder.data.room.movieNotes.MovieNotesDatabase
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private const val LIKED_MOVIES_DB_NAME = "LikedMovies.db"
        private const val MOVIE_NOTES_DB_NAME = "MovieNotes.db"
        private var dbLikedMovies: LikedMoviesDatabase? = null
        private var dbMovieNotes: MovieNotesDatabase? = null

        fun getLikedMoviesDao(): LikedMoviesDao {
            if (dbLikedMovies == null) {
                if (appInstance == null) throw IllformedLocaleException()
                else {
                    dbLikedMovies = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        LikedMoviesDatabase::class.java, LIKED_MOVIES_DB_NAME
                    ).build()
                }
            }
            return dbLikedMovies!!.likedMovieDao()
        }

        fun getMovieNotesDao(): MovieNotesDao {
            if (dbMovieNotes == null) {
                if (appInstance == null) throw IllformedLocaleException()
                else {
                    dbMovieNotes = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        MovieNotesDatabase::class.java, MOVIE_NOTES_DB_NAME
                    ).build()
                }
            }
            return dbMovieNotes!!.movieNotesDao()
        }

    }
}