package com.example.filmfinder.data.room.dao

import androidx.room.*
import com.example.filmfinder.data.room.model.LikedMoviesModel

@Dao
interface LikedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: LikedMoviesModel)

    @Delete
    fun delete(entity: LikedMoviesModel)

    @Update
    fun update(entity: LikedMoviesModel)

    @Query("SELECT * FROM liked_movies_entity")
    fun getAllLikedMovies(): List<LikedMoviesModel>

    @Query(
        "SELECT * FROM liked_movies_entity ORDER BY " +
                "CASE WHEN :isAsc = 1 THEN rating END ASC, " +
                "CASE WHEN :isAsc = 2 THEN rating END DESC "
    )
    fun getAllMoviesSortedByRating(isAsc: Int): List<LikedMoviesModel>
}