package com.example.filmfinder.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.repository.localRepo.NotesRepository
import com.example.filmfinder.domain.model.MovieEntity
import com.example.filmfinder.presentation.model.MovieNoteUiModel
import com.example.filmfinder.presentation.model.event.NoteAddEvent
import com.example.filmfinder.presentation.model.event.ViewModelEvent
import com.example.filmfinder.presentation.model.state.NoteAddState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteAddViewModel(
    private val repositoryNotes: NotesRepository,
    private val movie: MovieEntity
) : ViewModel() {

    private val viewState = MutableStateFlow(NoteAddState())

    val viewStateObserve: StateFlow<NoteAddState>
        get() = viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewState.update { viewState.value.copy(showError = true) }
    }

    private val scope = CoroutineScope(Dispatchers.Default + exceptionHandler)

    init {
        viewState.update { viewState.value.copy(movie = movie) }
    }

    fun sendEvent(event: ViewModelEvent) {
        when (event) {
            is NoteAddEvent.AddMovieClick -> {
                addNote(event.note)
            }
            is NoteAddEvent.ErrorShowed -> {
                viewState.update {
                    viewState.value.copy(showError = false)
                }
            }
        }
    }

    private fun addNote(note: String) {
        scope.launch {
            repositoryNotes.addNote(
                MovieNoteUiModel(
                    id = movie.id,
                    title = movie.title,
                    note = note,
                    posterPath = movie.image.orEmpty(),
                    date = movie.year.orEmpty()
                )
            )
        }
    }
}