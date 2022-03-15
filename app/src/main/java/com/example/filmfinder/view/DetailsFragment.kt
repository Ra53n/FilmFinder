package com.example.filmfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.databinding.DetailsFragmentBinding
import com.example.filmfinder.utils.MovieLoader
import com.example.filmfinder.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

const val BUNDLE_KEY = "KEY"

class DetailsFragment : Fragment(), MovieLoader.OnMovieLoaded {


    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_KEY)?.let {
            MovieLoader(this).loadMovie(it.id)
        }
    }

    private fun setData(movieDTO: MovieDTO) {
        with(binding) {
            movieName.text = movieDTO.title
            movieDescription.text = movieDTO.overview
            movieYear.text = movieDTO.releaseDate.substring(0,4)
            movieRating.text = movieDTO.voteAverage.toString()
        }
    }

    override fun onLoaded(movieDTO: MovieDTO) {
        setData(movieDTO)
    }

    override fun onFailed() {
        //TODO
    }
}