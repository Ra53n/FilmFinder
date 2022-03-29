package com.example.filmfinder.data.room.movieNotes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieNotesEntity::class], version = 1, exportSchema = false)
abstract class MovieNotesDatabase : RoomDatabase() {
    abstract fun movieNotesDao(): MovieNotesDao
}