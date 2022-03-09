package com.example.filmfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.Movie
import com.example.filmfinder.databinding.MainFragmentBinding
import com.example.filmfinder.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = MainFragmentBinding.inflate(inflater, container, false)
//        val view = binding.root
//        return view
//    }

//    override fun onDestroy() {
////        super.onDestroy()
////        _binding = null
//    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
////        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
////        val observer = Observer<AppState> { renderData(it) }
////        viewModel.getData().observe(viewLifecycleOwner, observer)
//    }

    private fun setData(movie: Movie) {
//        binding.movieName.text = movie.movieName
//        binding.movieDescription.text = movie.movieDescription
//        binding.movieYear.text = movie.movieYear.toString()
//        binding.movieRating.text = movie.movieRating.toString()
//        binding.movieImage.setImageResource(movie.image)
    }

//    private fun renderData(appState: AppState) {
//        when (appState) {
//            is AppState.Success -> {
//                showLoading(false)
//                setData(appState.movie)
//            }
//            is AppState.Loading -> {
//                showLoading(true)
//            }
//            is AppState.Error -> {
//                showLoading(false)
//                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("reload") { viewModel.getFilmFromLocalSource() }
//                    .show()
//            }
//        }
//    }
//
//    private fun showLoading(isShow: Boolean) {
//        binding.loadingLayout.isVisible = isShow
//        binding.mainLayout.isVisible = !isShow
//    }

}