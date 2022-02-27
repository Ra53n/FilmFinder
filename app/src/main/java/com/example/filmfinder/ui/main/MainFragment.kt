package com.example.filmfinder.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import app.moviebase.tmdb.Tmdb3
import app.moviebase.tmdb.model.TmdbExternalSource
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.Movie
import com.example.filmfinder.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<AppState> { renderData(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    private fun setData(movie: Movie){

    }
    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
                showLoading(false)
                setData(appState.movie)
            }
            is AppState.Loading ->{
                showLoading(true)
            }
            is AppState.Error ->{
                showLoading(false)
                Snackbar.make(binding.main,"Error",Snackbar.LENGTH_INDEFINITE)
                    .setAction("reload"){viewModel.getFilmFromLocalSource()}
                    .show()
            }
        }
        Toast.makeText(context, "Hello!", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isShow:Boolean){
        binding.loadingLayout.isVisible = isShow
        binding.mainLayout.isVisible = !isShow
    }

}