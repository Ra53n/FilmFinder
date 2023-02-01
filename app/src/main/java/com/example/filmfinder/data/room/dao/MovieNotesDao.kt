package com.example.filmfinder.data.room.dao

import androidx.room.*
import com.example.filmfinder.data.room.model.MovieNotesModel

@Dao
interface MovieNotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: MovieNotesModel)

    @Delete
    suspend fun delete(entity: MovieNotesModel)

    @Update
    suspend fun update(entity: MovieNotesModel)

    @Query("SELECT * FROM movie_notes_entity")
    suspend fun getAllMovieNotes(): List<MovieNotesModel>
}