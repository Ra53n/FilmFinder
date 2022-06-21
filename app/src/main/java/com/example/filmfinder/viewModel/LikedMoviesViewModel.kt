package com.example.filmfinder.viewModel

import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.repository.localRepo.RepositoryLocalImpl
import com.example.filmfinder.data.repository.localRepo.RepositoryLocalLikedMovies
import com.example.filmfinder.data.room.likedMovies.LikedMoviesEntity

class LikedMoviesViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryLikedMovies: RepositoryLocalLikedMovies = RepositoryLocalImpl()
) : ViewModel() {

    private var sortingAsc = false

    fun getData() = liveDataToObserver

    fun getLikedMoviesFromLocalSource() {
        Thread {
            liveDataToObserver.postValue(AppState.Loading)
            liveDataToObserver.postValue(AppState.Success(repositoryLikedMovies.getAllLikedMovies()))
        }.start()
    }

    fun getSortedLikedMoviesByRating(){
        Thread{
            liveDataToObserver.postValue(AppState.Loading)
            sortingAsc = !sortingAsc
            if (sortingAsc)
            liveDataToObserver.postValue(AppState.Success(repositoryLikedMovies.getLikedMoviesSortedByRating(2)))
            else
                liveDataToObserver.postValue(AppState.Success(repositoryLikedMovies.getLikedMoviesSortedByRating(1)))
        }.start()
    }

    fun switchSortingIcon(menuItem: MenuItem){
        if (sortingAsc)
            menuItem.setIcon(R.drawable.ic_baseline_keyboard_arrow_up_24)
        else
            menuItem.setIcon(R.drawable.ic_baseline_keyboard_arrow_down_24)
    }

    fun deleteNoteFromLocalRepository(entity: LikedMoviesEntity) {
        Thread {
            repositoryLikedMovies.deleteMovie(entity)
            liveDataToObserver.postValue(AppState.Success(repositoryLikedMovies.getAllLikedMovies()))
        }.start()
    }
}