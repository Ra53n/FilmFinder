package com.example.filmfinder.view.details

import android.app.IntentService
import android.content.Intent
import com.example.filmfinder.BuildConfig
import com.example.filmfinder.data.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailsService() : IntentService("") {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let { loadMovie(it.getIntExtra("EXTRA", 0)) }
    }

    private fun loadMovie(id: Int) {
        lateinit var httpsURLConnection: HttpsURLConnection
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
                val movieDTO: MovieDTO? = Gson().fromJson(result, MovieDTO::class.java)
                sendBroadcast(Intent(DETAILS_FRAGMENT_FILTER).apply {
                    putExtra(
                        BUNDLE_KEY_MOVIE,
                        movieDTO
                    )
                })
                Thread.sleep(350)
            } catch (exception: Exception) {
                val temp: String? = null
                sendBroadcast(Intent(DETAILS_FRAGMENT_FILTER).apply {
                    putExtra(
                        BUNDLE_KEY_MOVIE,
                        temp
                    )
                })
            } finally {
                httpsURLConnection.disconnect()
            }
        }.start()
    }
}