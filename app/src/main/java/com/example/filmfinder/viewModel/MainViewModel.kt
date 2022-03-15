package com.example.filmfinder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.Repository
import com.example.filmfinder.data.RepositoryImpl

class MainViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getData() = liveDataToObserver

    fun getFilmFromLocalSource() = getDataFromLocalSource()

    fun getFilmFromRemoteSource() = repository.getFilmFromServer()

    private fun getDataFromLocalSource() {
        with(liveDataToObserver) {
            postValue(AppState.Loading)
            if (Math.random() * 10 >= 9) {
                Thread {
                    Thread.sleep(3000)
                    postValue(AppState.Error(RuntimeException()))
                }.start()
            } else {
                Thread {
                    Thread.sleep(3000)
                    postValue(
                        AppState.Success(
                            repository.getPopularFilmFromLocaleStorage(),
                            repository.getUpcomingFilmFromLocaleStorage()
                        )
                    )
                }.start()
            }
        }
    }
}