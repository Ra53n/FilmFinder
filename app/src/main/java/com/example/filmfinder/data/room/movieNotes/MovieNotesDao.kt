package com.example.filmfinder.data.room.movieNotes

import androidx.room.*

@Dao
interface MovieNotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: MovieNotesEntity)

    @Delete
    fun delete(entity: MovieNotesEntity)

    @Update
    fun update(entity: MovieNotesEntity)

    @Query("SELECT * FROM movie_notes_entity")
    fun getAllMovieNotes(): List<MovieNotesEntity>
}