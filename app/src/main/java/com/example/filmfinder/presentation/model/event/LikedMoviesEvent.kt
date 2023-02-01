package com.example.filmfinder.presentation.model.event

import com.example.filmfinder.domain.model.MovieEntity

sealed class LikedMoviesEvent : ViewModelEvent {
    object SortMoviesByRatingClick : LikedMoviesEvent()

    class DeleteMovieClick(val movie: MovieEntity) : LikedMoviesEvent()

    object ErrorShowed : LikedMoviesEvent()

    object ClearFiltersClick : LikedMoviesEvent()

}