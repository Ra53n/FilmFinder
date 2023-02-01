package com.example.filmfinder.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.repository.remote.MovieRepository
import com.example.filmfinder.presentation.model.event.DetailsEvent
import com.example.filmfinder.presentation.model.event.ViewModelEvent
import com.example.filmfinder.presentation.model.state.DetailsState
import com.example.filmfinder.presentation.navigation.Navigator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DetailsViewModel(
    private val repository: MovieRepository,
    private val navigator: Navigator,
    private val movieId: String
) : ViewModel() {

    private val viewState = MutableStateFlow(DetailsState())

    val viewStateObserve: StateFlow<DetailsState>
        get() = viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("@@@", exception.message.orEmpty())
        viewState.update { viewState.value.copy(showError = true) }
    }

    private val scope = CoroutineScope(Dispatchers.Default + exceptionHandler)

    init {
        loadData()
    }


    private fun loadData() {
        viewState.update { viewState.value.copy(needShowLoading = true) }
        scope.launch {
            val movie = async {
                repository.getMovieById(movieId)
            }
            viewState.update {
                viewState.value.copy(
                    needShowLoading = false,
                    movie = movie.await()
                )
            }
        }
    }

    fun sendEvent(event: ViewModelEvent) {
        when (event) {
            is DetailsEvent.MenuItemNoteAddClick -> {
                viewState.value.movie?.let { navigator.navigateToAddNote(it) }
            }
            is DetailsEvent.ErrorShowed -> {
                viewState.update {
                    viewState.value.copy(showError = false)
                }
            }
        }
    }
}