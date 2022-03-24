package com.example.filmfinder.utils

import android.os.Handler
import android.os.Looper
import com.example.filmfinder.BuildConfig
import com.example.filmfinder.data.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieLoader(private val onMovieLoaded: OnMovieLoaded) {
    fun loadMovie(id: Int) {
        val handler = Handler(Looper.getMainLooper())
        lateinit var httpsURLConnection: HttpsURLConnection
        try {
            Thread {
                val url =
                    URL("https://api.themoviedb.org/3/movie/$id?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru")
                httpsURLConnection =
                    (url.openConnection() as HttpsURLConnection).apply {
                        requestMethod = "GET"
                        readTimeout = 2000
                    }
                try {
                    val br = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                    val result = br.lines().collect(Collectors.joining("\n"))
                    val movieDTO = Gson().fromJson(result, MovieDTO::class.java)
                    handler.post { onMovieLoaded.onLoaded(movieDTO) }
                } catch (e: Exception) {
                    handler.post { onMovieLoaded.onFailed() }
                }
            }.start()
        } finally {
            Thread.sleep(350)
            httpsURLConnection.disconnect()
        }
    }

    interface OnMovieLoaded {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed()
    }
}