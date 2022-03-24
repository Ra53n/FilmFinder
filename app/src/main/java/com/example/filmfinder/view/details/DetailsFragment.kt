package com.example.filmfinder.view.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.databinding.DetailsFragmentBinding
import com.example.filmfinder.viewModel.DetailsViewModel
import com.squareup.picasso.Picasso

const val BUNDLE_KEY = "KEY"
const val BUNDLE_KEY_MOVIE = "BUNDLE_KEY_MOVIE"
const val DETAILS_FRAGMENT_FILTER = "DETAILS_FRAGMENT_FILTER"

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

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
        viewModel.getData().observe(viewLifecycleOwner, (Observer<AppState> { renderData(it) }))
        arguments?.getParcelable<MovieDTO>(BUNDLE_KEY)?.let {
            viewModel.getMoveFromRemoteSource(it.id)
        }
    }

    private fun setData(movieDTO: MovieDTO) {
        with(binding) {
            movieName.text = movieDTO.title
            movieDescription.text = movieDTO.overview
            movieYear.text = movieDTO.releaseDate.substring(0, 4)
            movieRating.text =
                if (movieDTO.voteAverage != 0.0) movieDTO.voteAverage.toString() else "N/A"
            Picasso.with(context)
                .load("https://www.themoviedb.org/t/p/original" + movieDTO.posterPath)
                .into(movieImage)
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.DetailsSuccess -> {
                binding.detailsLoadingLayout.visibility = View.GONE
                setData(appState.movieDTO)
            }
            is AppState.Loading -> {
                binding.detailsLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                onFailed()
            }
        }
    }

    private fun onFailed() {
        AlertDialog.Builder(context).apply {
            setMessage(
                "Something gone wrong!"
            )

            setNegativeButton(
                "Back"
            ) { _, _ -> parentFragmentManager.popBackStack() }
        }.create().show()
    }
}