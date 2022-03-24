package com.example.filmfinder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.MovieList
import com.example.filmfinder.data.repository.Repository
import com.example.filmfinder.data.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getData() = liveDataToObserver

    fun getFilmFromLocalSource() = getMoviesFromRemoteSource()

    fun getMoviesFromRemoteSource() {
        repository.getPopularFilmFromService(callbackPopular)
        repository.getUpcomingFilmFromService(callbackUpcoming)
    }

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
//                    postValue(
//                        AppState.Success(
//                            repository.getPopularFilmFromService(),
//                            repository.getUpcomingFilmFromService()
//                        )
//                    )
                }.start()
            }
        }
    }

    private val callbackPopular = object : Callback<MovieList> {
        override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
            if (response.isSuccessful) {
                val popularMovies = response.body()
                liveDataToObserver.postValue(popularMovies?.let { AppState.SuccessPopularMovies(it) })
            }
        }

        override fun onFailure(call: Call<MovieList>, t: Throwable) {
            liveDataToObserver.postValue(AppState.Error(t))
        }
    }
    private val callbackUpcoming = object : Callback<MovieList> {
        override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
            if (response.isSuccessful) {
                val upcomingMovies = response.body()
                liveDataToObserver.postValue(upcomingMovies?.let { AppState.SuccessUpcomingMovies(it) })
            }
        }

        override fun onFailure(call: Call<MovieList>, t: Throwable) {
            liveDataToObserver.postValue(AppState.Error(t))
        }
    }
}