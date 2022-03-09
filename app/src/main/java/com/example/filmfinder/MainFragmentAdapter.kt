package com.example.filmfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmfinder.data.Movie


class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var movieData: List<Movie> = listOf()

    fun setMovie(data: List<Movie>) {
        this.movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(this.movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.main_recycler_item_movie_name_textview).text =
                movie.movieName
        }
    }
}