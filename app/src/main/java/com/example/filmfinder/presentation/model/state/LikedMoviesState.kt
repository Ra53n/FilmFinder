package com.example.filmfinder.presentation.model.state

import com.example.filmfinder.domain.model.MovieEntity

data class LikedMoviesState(
    val showError: Boolean = false,
    val needShowLoading: Boolean = false,
    val movies: List<MovieEntity>? = null,
    val isSortMoviesAsc: Boolean? = null
)