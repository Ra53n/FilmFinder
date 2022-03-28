package com.example.filmfinder.data.room

import androidx.room.*

@Dao
interface LikedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: LikedMoviesEntity)

    @Delete
    fun delete(entity: LikedMoviesEntity)

    @Update
    fun update(entity: LikedMoviesEntity)

    @Query("SELECT * FROM liked_movies_entity")
    fun getAllLikedMovies(): List<LikedMoviesEntity>
}