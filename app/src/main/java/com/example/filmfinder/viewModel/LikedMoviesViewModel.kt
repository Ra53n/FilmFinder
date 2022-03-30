package com.example.filmfinder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.repository.localRepo.RepositoryLocalLikedMovies
import com.example.filmfinder.data.repository.localRepo.RepositoryLocalImpl

class LikedMoviesViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryLikedMovies: RepositoryLocalLikedMovies = RepositoryLocalImpl()
) : ViewModel() {

    fun getData() = liveDataToObserver

    fun getLikedMoviesFromLocalSource() {
        Thread{
            liveDataToObserver.postValue(AppState.Loading)
            liveDataToObserver.postValue(AppState.Success(repositoryLikedMovies.getAllLikedMovies()))
        }.start()

    }
}