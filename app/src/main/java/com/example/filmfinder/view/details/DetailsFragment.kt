package com.example.filmfinder.view.details

import android.app.AlertDialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.databinding.DetailsFragmentBinding
import com.example.filmfinder.view.notes.NOTE_ADD_BUNDLE_KEY
import com.example.filmfinder.view.notes.NoteAddFragment
import com.example.filmfinder.viewModel.DetailsViewModel

const val DETAILS_FRAGMENT_BUNDLE_KEY = "KEY"

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this)[DetailsViewModel::class.java] }

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
        val movieDTO = arguments?.getParcelable<MovieDTO>(DETAILS_FRAGMENT_BUNDLE_KEY)
        movieDTO?.let {
            viewModel.getMoveFromRemoteSource(it.id)
        }
        view.setOnLongClickListener {
            val popupMenu = PopupMenu(context, it, Gravity.END)
            popupMenu.setOnMenuItemClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(
                        R.id.container,
                        NoteAddFragment.newInstance(Bundle().apply {
                            putParcelable(
                                NOTE_ADD_BUNDLE_KEY, movieDTO
                            )
                        })
                    )
                    .addToBackStack("").commit()
                true
            }
            popupMenu.inflate(R.menu.details_fragment_menu)
            popupMenu.show()
            true
        }
    }

    private fun setData(movieDTO: MovieDTO) {
        with(binding) {
            movieName.text = movieDTO.title
            movieDescription.text = movieDTO.overview
            movieDescription.movementMethod = ScrollingMovementMethod()
            movieYear.text = movieDTO.releaseDate.substring(0, 4)
            movieRating.text =
                if (movieDTO.voteAverage != 0.0) movieDTO.voteAverage.toString() else "N/A"
            movieImage.load("https://www.themoviedb.org/t/p/original" + movieDTO.posterPath)
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