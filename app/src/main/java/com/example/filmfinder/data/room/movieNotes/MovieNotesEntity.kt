package com.example.filmfinder.data.room.movieNotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_notes_entity")
data class MovieNotesEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val movieId: Long,
    val title: String,
    val note: String,
    val posterPath: String,
    val date: String
)
