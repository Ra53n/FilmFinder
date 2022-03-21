package com.example.filmfinder.view.details

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmfinder.data.Movie
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.databinding.DetailsFragmentBinding
import com.squareup.picasso.Picasso

const val BUNDLE_KEY = "KEY"
const val BUNDLE_KEY_MOVIE = "BUNDLE_KEY_MOVIE"
const val DETAILS_FRAGMENT_FILTER = "DETAILS_FRAGMENT_FILTER"

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val movieDTO: MovieDTO? = intent?.getParcelableExtra<MovieDTO>(BUNDLE_KEY_MOVIE)
            if (movieDTO != null) {
                setData(movieDTO)
            } else {
                onFailed()
            }
        }
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
        requireActivity().unregisterReceiver(receiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_KEY)?.let {
            val intent = Intent(requireActivity(), DetailsService::class.java)
            intent.putExtra("EXTRA", it.id)
            requireActivity().startService(intent)
        }
        requireActivity().registerReceiver(receiver, IntentFilter(DETAILS_FRAGMENT_FILTER))
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