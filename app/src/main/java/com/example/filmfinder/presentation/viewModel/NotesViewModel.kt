package com.example.filmfinder.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.repository.localRepo.NotesRepository
import com.example.filmfinder.presentation.model.MovieNoteUiModel
import com.example.filmfinder.presentation.model.event.NoteEvent
import com.example.filmfinder.presentation.model.event.ViewModelEvent
import com.example.filmfinder.presentation.model.state.NotesState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel(
    private val repositoryNotes: NotesRepository
) : ViewModel() {

    private val viewState = MutableStateFlow(NotesState())

    val viewStateObserve: StateFlow<NotesState>
        get() = viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewState.update { viewState.value.copy(showError = true) }
    }

    private val scope = CoroutineScope(Dispatchers.Default + exceptionHandler)

    fun sendEvent(event: ViewModelEvent) {
        when (event) {
            is NoteEvent.MovieDelete -> {
                deleteMovie(event.movie)
            }
            is NoteEvent.ErrorShowed -> {
                viewState.update {
                    viewState.value.copy(showError = false)
                }
            }
        }
    }

    private fun deleteMovie(entity: MovieNoteUiModel) {
        scope.launch {
            repositoryNotes.deleteNote(entity)
        }
    }

    fun getNotes() {
        scope.launch {
            val notes = async { repositoryNotes.getAllNotes() }
            viewState.update { viewState.value.copy(notes = notes.await()) }
        }
    }
}