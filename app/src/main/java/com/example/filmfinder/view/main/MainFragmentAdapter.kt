package com.example.filmfinder.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmfinder.R
import com.example.filmfinder.data.Movie
import com.example.filmfinder.view.OnItemClickListener

class MainFragmentAdapter(val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var movieData: List<Movie> = listOf()

    fun setMovie(data: List<Movie>) {
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
        fun bind(movie: Movie) {
            with(itemView) {
                findViewById<TextView>(R.id.main_recycler_item_movie_name_textview).text =
                    movie.movieName
                findViewById<ImageView>(R.id.main_recycler_item_movie_image_view)
                    .setImageResource(movie.image)
                findViewById<TextView>(R.id.main_recycler_item_movie_year).text =
                    movie.movieYear.toString()
                findViewById<TextView>(R.id.main_recycler_item_movie_rating).text =
                    movie.movieRating.toString()
                setOnClickListener { onItemClickListener.onItemClick(movie) }
            }
        }
    }
}