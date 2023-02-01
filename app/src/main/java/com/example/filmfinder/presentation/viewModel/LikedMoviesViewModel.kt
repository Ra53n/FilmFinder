package com.example.filmfinder.presentation.viewModel

import android.view.MenuItem
import androidx.lifecycle.ViewModel
import com.example.filmfinder.R
import com.example.filmfinder.domain.interactor.LikedMovieInteractor
import com.example.filmfinder.domain.model.MovieEntity
import com.example.filmfinder.presentation.model.event.LikedMoviesEvent
import com.example.filmfinder.presentation.model.event.ViewModelEvent
import com.example.filmfinder.presentation.model.state.LikedMoviesState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LikedMoviesViewModel(
    private val likedMovieInteractor: LikedMovieInteractor
) : ViewModel() {


    private val viewState = MutableStateFlow(LikedMoviesState())

    val viewStateObserve: StateFlow<LikedMoviesState>
        get() = viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewState.update { viewState.value.copy(showError = true) }
    }

    private val scope = CoroutineScope(Dispatchers.Default + exceptionHandler)


    private var sortingAsc = false

    init {
        loadLikedMovies()
    }

    fun loadLikedMovies() {
        showLoading()
        scope.launch {
            val movies = async { likedMovieInteractor.getAllLikedMovies() }
            viewState.update {
                viewState.value.copy(needShowLoading = false, movies = movies.await())
            }
        }
    }

    private fun showLoading() {
        viewState.update { viewState.value.copy(needShowLoading = true) }
    }

    fun switchSortingIcon(menuItem: MenuItem) {
        if (sortingAsc)
            menuItem.setIcon(R.drawable.ic_baseline_keyboard_arrow_up_24)
        else
            menuItem.setIcon(R.drawable.ic_baseline_keyboard_arrow_down_24)
    }

    private fun deleteMovie(movie: MovieEntity) {
        scope.launch {
            likedMovieInteractor.deleteMovieFromLikes(movie)
        }
    }

    private fun sortMoviesByRating() {
        sortingAsc = !sortingAsc
        showLoading()
        scope.launch {
            viewState.update {
                viewState.value.copy(
                    needShowLoading = false,
                    movies = likedMovieInteractor.getLikedMoviesSortedByRating(sortingAsc)
                )
            }
        }
    }

    fun sendEvent(event: ViewModelEvent) {
        when (event) {
            is LikedMoviesEvent.SortMoviesByRatingClick -> {
                sortMoviesByRating()
            }
            is LikedMoviesEvent.ErrorShowed -> {
                viewState.update {
                    viewState.value.copy(showError = false)
                }
            }
            is LikedMoviesEvent.DeleteMovieClick -> {
                deleteMovie(event.movie)
            }
            is LikedMoviesEvent.ClearFiltersClick -> {
                loadLikedMovies()
            }
        }
    }
}