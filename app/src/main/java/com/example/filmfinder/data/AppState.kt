package com.example.filmfinder.data

sealed class AppState {
    data class Success(val popularMovie: List<Movie>, val upcomingMovie: List<Movie>) : AppState()
    data class SuccessPopularMovies(val popularMovies: MovieList) : AppState()
    data class SuccessUpcomingMovies(val upcomingMovies: MovieList) : AppState()
    data class DetailsSuccess(val movieDTO: MovieDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
