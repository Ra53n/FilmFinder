package com.example.filmfinder.data.room.likedMovies

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LikedMoviesEntity::class], version = 1, exportSchema = false)
abstract class LikedMoviesDatabase : RoomDatabase() {
    abstract fun likedMovieDao(): LikedMoviesDao
}