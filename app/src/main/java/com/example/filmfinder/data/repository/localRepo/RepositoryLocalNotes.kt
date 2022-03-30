package com.example.filmfinder.data.repository.localRepo

import com.example.filmfinder.data.room.movieNotes.MovieNotesEntity

interface RepositoryLocalNotes {
    fun getAllNotes(): List<MovieNotesEntity>
    fun addNote(entity: MovieNotesEntity)
    fun deleteNote(entity: MovieNotesEntity)
}