package com.example.filmfinder.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val liveDataToObserver: MutableLiveData<Any>) : ViewModel() {
    fun getData(): LiveData<Any> {
        getDataFromLocalSource()
        return liveDataToObserver
    }

    private fun getDataFromLocalSource(){
        Thread{
            Thread.sleep(1000)
            liveDataToObserver.postValue("Hello!")
        }.start()
    }
}