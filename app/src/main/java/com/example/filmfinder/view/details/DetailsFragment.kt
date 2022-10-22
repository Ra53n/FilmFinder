package com.example.filmfinder.view.details

import android.app.AlertDialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.databinding.DetailsFragmentBinding
import com.example.filmfinder.view.notes.NOTE_ADD_BUNDLE_KEY
import com.example.filmfinder.view.notes.NoteAddFragment
import com.example.filmfinder.viewModel.DetailsViewModel

const val DETAILS_FRAGMENT_BUNDLE_KEY = "KEY"

class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val binding: DetailsFragmentBinding by viewBinding()
    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this)[DetailsViewModel::class.java] }

    private var movieDTO: MovieDTO? = null

    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, (Observer<AppState> { renderData(it) }))
        this.movieDTO = arguments?.getParcelable<MovieDTO>(DETAILS_FRAGMENT_BUNDLE_KEY)
        getMovie()
        initListeners(view)
    }

    private fun getMovie() {
        movieDTO?.let {
            viewModel.getMoveFromRemoteSource(it.id)
        }
    }

    private fun initListeners(view: View) {
        view.setOnLongClickListener {
            val popupMenu = PopupMenu(context, it, Gravity.END)
            popupMenu.setOnMenuItemClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
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
                if (movieDTO.voteAverage != 0.0) movieDTO.voteAverage.toString() else resources.getString(
                    R.string.n_a
                )
            movieImage.load("${resources.getString(R.string.film_poster_endpoint)}${movieDTO.posterPath}")
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
                resources.getString(R.string.something_gone_wrong)
            )

            setNegativeButton(
                resources.getString(R.string.back)
            ) { _, _ -> parentFragmentManager.popBackStack() }
        }.create().show()
    }
}