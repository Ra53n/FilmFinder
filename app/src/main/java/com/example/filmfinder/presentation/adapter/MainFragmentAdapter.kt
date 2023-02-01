package com.example.filmfinder.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.databinding.MainRecyclerItemBinding
import com.example.filmfinder.domain.model.MovieEntity

class MainFragmentAdapter(
    val onMovieClick: (MovieEntity) -> Unit,
    val onLikeMovieClick: (MovieEntity) -> Unit
) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var movieData: List<MovieEntity> = listOf()

    fun setMovies(data: List<MovieEntity>) {
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

        private val binding = MainRecyclerItemBinding.bind(itemView)

        fun bind(movie: MovieEntity) {
            with(itemView) {
                binding.movieTitle.text = movie.title
                binding.movieYear.text = movie.year
                if (movie.imDBRating != null) {
                    binding.movieRating.text = movie.imDBRating
                } else {
                    binding.movieRating.visibility = View.GONE
                }

                binding.movieImage.load(movie.image)

                binding.like.bringToFront()
                setOnClickListener { onMovieClick(movie) }
                binding.like.setOnClickListener { onLikeMovieClick(movie) }
            }
        }
    }
}