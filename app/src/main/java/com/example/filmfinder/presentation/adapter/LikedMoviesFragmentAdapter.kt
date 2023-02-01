package com.example.filmfinder.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.databinding.LikedMoviesRecyclerItemBinding
import com.example.filmfinder.domain.model.MovieEntity

class LikedMoviesFragmentAdapter(
    val onDeleteMovieClick: (MovieEntity) -> Unit,
) : RecyclerView.Adapter<LikedMoviesFragmentAdapter.LikedMoviesViewHolder>() {

    private var movieData: List<MovieEntity> = listOf()

    fun setMovie(data: List<MovieEntity>) {
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

        private val binding = LikedMoviesRecyclerItemBinding.bind(itemView)
        fun bind(movie: MovieEntity) {
            binding.movieTitle.text = movie.title
            binding.movieYear.text = movie.year
            binding.movieRating.text = movie.imDBRating
            binding.deleteMovieButton.bringToFront()
            binding.deleteMovieButton.setOnClickListener { onDeleteMovieClick(movie) }
            binding.movieImage.load(movie.image)
        }
    }
}