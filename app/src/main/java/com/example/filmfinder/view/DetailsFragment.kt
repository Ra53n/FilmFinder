package com.example.filmfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.Movie
import com.example.filmfinder.databinding.DetailsFragmentBinding
import com.example.filmfinder.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

const val BUNDLE_KEY = "KEY"

class DetailsFragment : Fragment() {


    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(BUNDLE_KEY)
        if(movie!=null){
            setData(movie)
        }
    }

    private fun setData(movie: Movie) {
        binding.movieName.text = movie.movieName
        binding.movieDescription.text = movie.movieDescription
        binding.movieYear.text = movie.movieYear.toString()
        binding.movieRating.text = movie.movieRating.toString()
        binding.movieImage.setImageResource(movie.image)
    }


}