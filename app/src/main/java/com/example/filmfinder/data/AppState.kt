package com.example.filmfinder.data

sealed class AppState {
    data class Success(val popularMovie: List<Movie>,val upcomingMovie: List<Movie>) : AppState()
    data class DetailsSuccess(val movieDTO: MovieDTO):AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
