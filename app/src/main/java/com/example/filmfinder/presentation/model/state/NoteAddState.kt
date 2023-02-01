package com.example.filmfinder.presentation.model.state

import com.example.filmfinder.domain.model.MovieEntity
import com.example.filmfinder.presentation.model.event.ViewModelEvent

data class NoteAddState(
    val movie: MovieEntity? = null,
    val showError: Boolean = false
) : ViewModelEvent

