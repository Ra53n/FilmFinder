package com.example.filmfinder.presentation.model.event

import com.example.filmfinder.domain.model.MovieEntity

sealed class MainEvent : ViewModelEvent {

    object ErrorShowed : MainEvent()

    class MovieLiked(val movie: MovieEntity) : MainEvent()

    class MovieClicked(val movie: MovieEntity) : MainEvent()

    object MenuItemLikedMoviesClick : MainEvent()

    object MenuItemNotesClick : MainEvent()

    object MenuItemContactsClick : MainEvent()

    object MenuItemMapsClick : MainEvent()

}