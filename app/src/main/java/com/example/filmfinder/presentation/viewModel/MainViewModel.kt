package com.example.filmfinder.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.repository.remote.MovieRepository
import com.example.filmfinder.domain.interactor.LikedMovieInteractor
import com.example.filmfinder.domain.model.MovieEntity
import com.example.filmfinder.presentation.model.event.MainEvent
import com.example.filmfinder.presentation.model.event.ViewModelEvent
import com.example.filmfinder.presentation.model.state.MainState
import com.example.filmfinder.presentation.navigation.Navigator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val movieRepository: MovieRepository,
    private val likedMovieInteractor: LikedMovieInteractor,
    private val navigator: Navigator
) : ViewModel() {

    private val viewState = MutableStateFlow(MainState())

    val viewStateObserve: StateFlow<MainState>
        get() = viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewState.update {
            Log.e("@@@", exception.message.orEmpty())
            viewState.value.copy(showError = true)
        }
    }

    private val scope = CoroutineScope(Dispatchers.Default + exceptionHandler)


    fun getMovies(needToShowAdult: Boolean) {
        scope.launch {

            viewState.update { viewState.value.copy(needShowLoading = true) }
            val popularMovies = async { movieRepository.getPopularMovies(needToShowAdult) }
            val upcomingMovies = async { movieRepository.getComingMovies(needToShowAdult) }

            viewState.update {
                viewState.value.copy(
                    needShowLoading = false,
                    popularMovies = popularMovies.await(),
                    upcomingMovies = upcomingMovies.await()
                )
            }
        }
    }

    fun sendEvent(event: ViewModelEvent) {
        when (event) {
            is MainEvent.MovieLiked -> {
                likeMovie(event.movie)
            }
            is MainEvent.MovieClicked -> {
                movieClicked(event.movie)
            }
            is MainEvent.ErrorShowed -> {
                viewState.update {
                    viewState.value.copy(showError = false)
                }
            }
            is MainEvent.MenuItemNotesClick -> {
                navigator.navigateToNotes()
            }
            is MainEvent.MenuItemLikedMoviesClick -> {
                navigator.navigateToLikedMovies()
            }
            is MainEvent.MenuItemContactsClick -> {
                navigator.navigateToContacts()
            }
            is MainEvent.MenuItemMapsClick -> {
                navigator.navigateToMaps()
            }
        }
    }

    private fun likeMovie(movie: MovieEntity) {
        scope.launch {
            likedMovieInteractor.likeMovie(movie)
        }
    }

    private fun movieClicked(movie: MovieEntity) {
        navigator.navigateToDetails(movie.id)
    }
}