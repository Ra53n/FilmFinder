package com.example.filmfinder.data

sealed class AppState {
    data class Success(val likedMovies: List<Movie>) : AppState()
    data class SuccessPopularMovies(val popularMovies: MovieListDTO) : AppState()
    data class SuccessUpcomingMovies(val upcomingMovies: MovieListDTO) : AppState()
    data class DetailsSuccess(val movieDTO: MovieDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
