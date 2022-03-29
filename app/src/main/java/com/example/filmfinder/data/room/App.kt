package com.example.filmfinder.data.room

import android.app.Application
import androidx.room.Room
import java.util.*

class App :Application(){
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance:App? = null
        const val DB_NAME = "LikedMovies.db"
        private var db:LikedMoviesDatabase? = null

        fun getLikedMoviesDao():LikedMoviesDao{
            if(db == null){
                if(appInstance == null) throw IllformedLocaleException()
                else{
                    db = Room.databaseBuilder(appInstance!!.applicationContext,LikedMoviesDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!.likedMovieDao()
        }

    }
}