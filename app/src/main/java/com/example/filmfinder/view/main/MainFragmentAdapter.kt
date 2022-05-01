package com.example.filmfinder.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.filmfinder.App
import com.example.filmfinder.R
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.data.room.likedMovies.LikedMoviesEntity
import com.example.filmfinder.view.OnItemClickListener

class MainFragmentAdapter(val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var movieData: List<MovieDTO> = listOf()

    fun setMovie(data: List<MovieDTO>) {
        this.movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_item, parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(this.movieData[position])
    }

    override fun getItemCount() = movieData.size


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: MovieDTO) {
            with(itemView) {
                findViewById<TextView>(R.id.main_recycler_item_movie_name_textview).text =
                    movie.title
                findViewById<TextView>(R.id.main_recycler_item_movie_year).text =
                    movie.releaseDate.substring(0, 4)
                findViewById<TextView>(R.id.main_recycler_item_movie_rating).text =
                    if (movie.voteAverage != 0.0) movie.voteAverage.toString() else "N/A"

                findViewById<ImageView>(R.id.main_recycler_item_movie_image_view).load("https://www.themoviedb.org/t/p/original" + movie.posterPath)
                setOnClickListener { onItemClickListener.onItemClick(movie) }
                findViewById<ImageView>(R.id.main_recycler_item_movie_like_image_view).bringToFront()
                findViewById<ImageView>(R.id.main_recycler_item_movie_like_image_view).setOnClickListener {
                    Thread {
                        App.getLikedMoviesDao().insert(
                            LikedMoviesEntity(
                                movie.id,
                                movie.title,
                                movie.overview,
                                movie.releaseDate,
                                movie.voteAverage.toString(),
                                movie.posterPath
                            )
                        )
                    }.start()
                }
            }
        }
    }
}