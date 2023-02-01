package com.example.filmfinder.data.repository.localRepo

import com.example.filmfinder.presentation.model.MovieNoteUiModel

interface NotesRepository {
    suspend fun getAllNotes(): List<MovieNoteUiModel>
    suspend fun addNote(entity: MovieNoteUiModel)
    suspend fun deleteNote(entity: MovieNoteUiModel)
}