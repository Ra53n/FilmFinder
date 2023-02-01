package com.example.filmfinder.presentation.model.state

import com.example.filmfinder.domain.model.MovieEntity

data class MainState(
    val needShowLoading: Boolean = false,
    val popularMovies: List<MovieEntity> = emptyList(),
    val upcomingMovies: List<MovieEntity> = emptyList(),
    val showError: Boolean = false
)