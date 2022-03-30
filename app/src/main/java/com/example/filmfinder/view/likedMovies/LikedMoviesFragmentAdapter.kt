package com.example.filmfinder.view.likedMovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.room.likedMovies.LikedMoviesEntity

class LikedMoviesFragmentAdapter(private val controller: Controller) :
    RecyclerView.Adapter<LikedMoviesFragmentAdapter.LikedMoviesViewHolder>() {
    private var movieData: List<Movie> = listOf()

    interface Controller {
        fun onDeleteItemClick(entity: LikedMoviesEntity)
    }

    fun setMovie(data: List<Movie>) {
        this.movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LikedMoviesViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.liked_movies_recycler_item, parent, false)
    )

    override fun onBindViewHolder(holder: LikedMoviesViewHolder, position: Int) {
        holder.bind(this.movieData[position])
    }

    override fun getItemCount() = movieData.size


    inner class LikedMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            with(itemView) {
                findViewById<TextView>(R.id.liked_movies__recycler_item_movie_name_textview).text =
                    movie.title

                findViewById<TextView>(R.id.liked_movies__recycler_item_movie_year).text =
                    movie.realiseDate.substring(0, 4)

                findViewById<TextView>(R.id.liked_movies__recycler_item_movie_rating).text =
                    if (movie.rating != "0.0") movie.rating else "N/A"

                findViewById<ImageView>(R.id.liked_movies__recycler_item_delete_image_view).bringToFront()

                findViewById<ImageView>(R.id.liked_movies__recycler_item_delete_image_view).setOnClickListener {
                    controller.onDeleteItemClick(with(movie) {
                        LikedMoviesEntity(id, title, overview, realiseDate, rating, posterPath)
                    })
                }

                findViewById<ImageView>(R.id.liked_movies__recycler_item_movie_image_view).load("https://www.themoviedb.org/t/p/original" + movie.posterPath)
            }
        }
    }
}