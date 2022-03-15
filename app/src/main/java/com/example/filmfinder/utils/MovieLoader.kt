package com.example.filmfinder.utils

import android.os.Handler
import android.os.Looper
import com.example.filmfinder.data.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieLoader(private val onMovieLoaded: OnMovieLoaded) {
    fun loadMovie(id: Int) {
        Thread{
            val handler = Handler(Looper.getMainLooper())
            val url =
                URL("https://api.themoviedb.org/3/movie/$id?api_key=8d59fe8d268e9cac0609c1c4b077a9fd&language=ru")
            val httpsURLConnection =
                (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 2000
                }
            val br = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val result = br.lines().collect(Collectors.joining("\n"))
            val movieDTO = Gson().fromJson(result, MovieDTO::class.java)
            handler.post { onMovieLoaded.onLoaded(movieDTO) }
        }.start()

    }

    interface OnMovieLoaded {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed() //TODO
    }
}