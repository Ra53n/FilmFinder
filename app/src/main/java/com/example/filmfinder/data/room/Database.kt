package com.example.filmfinder.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmfinder.data.room.dao.LikedMoviesDao
import com.example.filmfinder.data.room.model.LikedMoviesModel
import com.example.filmfinder.data.room.dao.MovieNotesDao
import com.example.filmfinder.data.room.model.MovieNotesModel

@Database(entities = [LikedMoviesModel::class, MovieNotesModel::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun likedMovieDao(): LikedMoviesDao

    abstract fun movieNotesDao(): MovieNotesDao
}