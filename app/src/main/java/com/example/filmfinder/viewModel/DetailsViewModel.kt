package com.example.filmfinder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.data.repository.DetailsRepository
import com.example.filmfinder.data.repository.DetailsRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepository = DetailsRepositoryImpl()
) : ViewModel() {
    fun getData() = liveDataToObserver
    fun getMoveFromRemoteSource(id: Long) {
        liveDataToObserver.postValue(AppState.Loading)
        repository.getMovieFromServer(id, callback)
    }

    private val callback = object : Callback<MovieDTO> {
        override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
            if (response.isSuccessful) {
                val movieDTO = response.body()
                liveDataToObserver.postValue(movieDTO?.let { AppState.DetailsSuccess(it) })
            }
        }

        override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
            liveDataToObserver.postValue(AppState.Error(t))
        }
    }
}