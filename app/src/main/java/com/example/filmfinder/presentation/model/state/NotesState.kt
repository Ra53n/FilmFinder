package com.example.filmfinder.presentation.model.state

import com.example.filmfinder.presentation.model.MovieNoteUiModel
import com.example.filmfinder.presentation.model.event.ViewModelEvent

data class NotesState(
    val notes: List<MovieNoteUiModel> = emptyList(),
    val showError: Boolean = false
): ViewModelEvent