package com.example.filmfinder.presentation.model.event

import com.example.filmfinder.presentation.model.MovieNoteUiModel

sealed class NoteEvent: ViewModelEvent {
    object ErrorShowed : NoteEvent()
    class MovieDelete(val movie: MovieNoteUiModel) : NoteEvent()
}