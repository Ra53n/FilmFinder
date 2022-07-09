package com.example.filmfinder.data.room.likedMovies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_movies_entity")
data class LikedMoviesEntity(
    @PrimaryKey(autoGenerate = false) val movieId: Long,
    val title: String,
    val overview: String,
    val realiseDate: String,
    val rating: String,
    val posterPath: String
)
