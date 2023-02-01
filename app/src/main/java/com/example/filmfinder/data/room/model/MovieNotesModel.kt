package com.example.filmfinder.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_notes_entity")
data class MovieNotesModel(
    @PrimaryKey val id: String,
    val title: String,
    val note: String,
    val posterPath: String,
    val date: String
)
