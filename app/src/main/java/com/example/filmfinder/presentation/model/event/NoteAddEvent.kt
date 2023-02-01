package com.example.filmfinder.presentation.model.event


sealed class NoteAddEvent : ViewModelEvent {
    class AddMovieClick(val note: String) : NoteAddEvent()
    object ErrorShowed : MainEvent()
}