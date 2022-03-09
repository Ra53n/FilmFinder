package com.example.filmfinder.data

sealed class AppState {
    data class Success(val movie: Movie) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
