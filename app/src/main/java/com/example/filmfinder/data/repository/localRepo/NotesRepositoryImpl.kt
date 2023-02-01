package com.example.filmfinder.data.repository.localRepo

import com.example.filmfinder.data.mapper.MovieNotesModelMapper
import com.example.filmfinder.data.room.Database
import com.example.filmfinder.presentation.model.MovieNoteUiModel

class NotesRepositoryImpl(
    private val database: Database,
    private val mapper: MovieNotesModelMapper
) : NotesRepository {
    override suspend fun getAllNotes(): List<MovieNoteUiModel> {
        return database.movieNotesDao().getAllMovieNotes().map { mapper.map(it) }
    }

    override suspend fun addNote(entity: MovieNoteUiModel) {
        database.movieNotesDao().insert(mapper.map(entity))
    }

    override suspend fun deleteNote(entity: MovieNoteUiModel) {
        database.movieNotesDao().delete(mapper.map(entity))
    }
}