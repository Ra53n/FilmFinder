package com.example.filmfinder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.repository.localRepo.RepositoryLocalImpl
import com.example.filmfinder.data.repository.localRepo.RepositoryLocalNotes
import com.example.filmfinder.data.room.movieNotes.MovieNotesEntity

class NotesViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryNotes: RepositoryLocalNotes = RepositoryLocalImpl()
) : ViewModel() {

    fun getData() = liveDataToObserver

    fun getNotesFromLocalSource() {
        Thread {
            liveDataToObserver.postValue(AppState.SuccessNotes(repositoryNotes.getAllNotes()))
        }.start()
    }

    fun deleteNoteFromLocalRepository(entity: MovieNotesEntity) {
        Thread {
            repositoryNotes.deleteNote(entity)
            liveDataToObserver.postValue(AppState.SuccessNotes(repositoryNotes.getAllNotes()))
        }.start()
    }
}