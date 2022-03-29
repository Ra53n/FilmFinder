package com.example.filmfinder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.MovieListDTO
import com.example.filmfinder.data.repository.remoteRepo.RepositoryRemote
import com.example.filmfinder.data.repository.remoteRepo.RepositoryRemoteImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryRemote: RepositoryRemote = RepositoryRemoteImpl()
) : ViewModel() {
    fun getData() = liveDataToObserver

    fun getMoviesFromServer() = getMoviesFromRemoteSource()

    fun getMoviesWitchAdultFromServer() = getMoviesAdultFromRemoteSource()

    private fun getMoviesFromRemoteSource() {
        repositoryRemote.getPopularFilmFromService(false, callbackPopular)
        repositoryRemote.getUpcomingFilmFromService(callbackUpcoming)
    }

    private fun getMoviesAdultFromRemoteSource() {
        repositoryRemote.getPopularFilmFromService(true, callbackPopular)
        repositoryRemote.getUpcomingFilmFromService(callbackUpcoming)
    }


    private val callbackPopular = object : Callback<MovieListDTO> {
        override fun onResponse(call: Call<MovieListDTO>, response: Response<MovieListDTO>) {
            if (response.isSuccessful) {
                val popularMovies = response.body()
                liveDataToObserver.postValue(AppState.Loading)
                Thread.sleep(1000)
                liveDataToObserver.postValue(popularMovies?.let { AppState.SuccessPopularMovies(it) })
            }
        }

        override fun onFailure(call: Call<MovieListDTO>, t: Throwable) {
            liveDataToObserver.postValue(AppState.Error(t))
        }
    }
    private val callbackUpcoming = object : Callback<MovieListDTO> {
        override fun onResponse(call: Call<MovieListDTO>, response: Response<MovieListDTO>) {
            if (response.isSuccessful) {
                val upcomingMovies = response.body()
                liveDataToObserver.postValue(upcomingMovies?.let { AppState.SuccessUpcomingMovies(it) })
            }
        }

        override fun onFailure(call: Call<MovieListDTO>, t: Throwable) {
            liveDataToObserver.postValue(AppState.Error(t))
        }
    }
}