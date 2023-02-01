package com.example.filmfinder.presentation.model.state

import com.example.filmfinder.domain.model.MovieEntity

data class DetailsState(
    val showError: Boolean = false,
    val needShowLoading: Boolean = false,
    val movie: MovieEntity? = null
)