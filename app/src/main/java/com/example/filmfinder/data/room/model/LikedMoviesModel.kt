package com.example.filmfinder.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_movies_entity")
data class LikedMoviesModel(
    @PrimaryKey val movieId: String,
    val title: String,
    val plot: String,
    val realiseDate: String,
    val rating: String,
    val posterPath: String
)
