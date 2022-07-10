package com.example.filmfinder.data.room.likedMovies

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

    @Query(
        "SELECT * FROM liked_movies_entity ORDER BY " +
                "CASE WHEN :isAsc = 1 THEN rating END ASC, " +
                "CASE WHEN :isAsc = 2 THEN rating END DESC "
    )
    fun getAllMoviesSortedByRating(isAsc: Int): List<LikedMoviesEntity>
}