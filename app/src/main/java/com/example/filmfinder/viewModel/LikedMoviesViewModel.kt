package com.example.filmfinder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.repository.RepositoryLocal
import com.example.filmfinder.data.repository.RepositoryLocalImpl

class LikedMoviesViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryLocal = RepositoryLocalImpl()
) : ViewModel() {

    fun getData() = liveDataToObserver

    fun getLikedMoviesFromLocalSource() {
        liveDataToObserver.postValue(AppState.Loading)
        liveDataToObserver.postValue(AppState.Success(repository.getAllLikedMovies()))
    }
}