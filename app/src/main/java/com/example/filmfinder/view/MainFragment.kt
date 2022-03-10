package com.example.filmfinder.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmfinder.MainFragmentAdapter
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.Movie
import com.example.filmfinder.databinding.MainFragmentBinding
import com.example.filmfinder.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter()
    private val adapterSecond = MainFragmentAdapter()


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
        bindAdapters()

    }

    private fun bindAdapters() {
        binding.mainFragmentRecyclerViewUp.adapter = this.adapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.mainFragmentRecyclerViewUp.layoutManager = linearLayoutManager

        binding.mainFragmentRecyclerViewDown.adapter = this.adapterSecond
        val linearLayoutManager2 = LinearLayoutManager(context)
        linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        binding.mainFragmentRecyclerViewDown.layoutManager = linearLayoutManager2
        viewModel.getFilmFromLocalSource()
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showLoading(false)
                adapter.setMovie(appState.popularMovie)
                adapterSecond.setMovie(appState.upcomingMovie)
            }
            is AppState.Loading -> {
                showLoading(true)
            }
            is AppState.Error -> {
                showLoading(false)
                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("reload") { viewModel.getFilmFromLocalSource() }
                    .show()
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.loadingLayout.isVisible = isShow
        binding.mainLayout.isVisible = !isShow
    }

}