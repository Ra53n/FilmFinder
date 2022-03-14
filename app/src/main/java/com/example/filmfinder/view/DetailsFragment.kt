package com.example.filmfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmfinder.data.Movie
import com.example.filmfinder.databinding.DetailsFragmentBinding
import com.example.filmfinder.viewModel.MainViewModel

const val BUNDLE_KEY = "KEY"

class DetailsFragment : Fragment() {


    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }

    private lateinit var viewModel: MainViewModel

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
        arguments?.getParcelable<Movie>(BUNDLE_KEY)?.let { setData(it) }
    }

    private fun setData(movie: Movie) {
        with(binding) {
            movieName.text = movie.movieName
            movieDescription.text = movie.movieDescription
            movieYear.text = movie.movieYear.toString()
            movieRating.text = movie.movieRating.toString()
            movieImage.setImageResource(movie.image)
        }
    }
}