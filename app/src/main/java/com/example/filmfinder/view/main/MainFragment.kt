package com.example.filmfinder.view.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmfinder.ConnectivityActionBroadcastReceiver
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.databinding.MainFragmentBinding
import com.example.filmfinder.view.OnItemClickListener
import com.example.filmfinder.view.details.BUNDLE_KEY
import com.example.filmfinder.view.details.DetailsFragment
import com.example.filmfinder.view.snackBarWithAction
import com.example.filmfinder.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnItemClickListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter(this)
    private val adapterSecond = MainFragmentAdapter(this)
    private val connectivityActionBroadcastReceiver = ConnectivityActionBroadcastReceiver()


    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, (Observer { renderData(it) }))
        bindAdapters()
        requireActivity().registerReceiver(
            connectivityActionBroadcastReceiver, IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
            )
        )
    }

    private fun bindAdapters() {
        with(binding) {
            val linearLayout = {
                LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
            mainFragmentRecyclerViewUp.adapter = adapter
            mainFragmentRecyclerViewUp.layoutManager = linearLayout()
            mainFragmentRecyclerViewDown.adapter = adapterSecond
            mainFragmentRecyclerViewDown.layoutManager = linearLayout()
            viewModel.getFilmFromLocalSource()
        }
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessPopularMovies -> {
                showLoading(false)
                Thread.sleep(1000)
                adapter.setMovie(appState.popularMovies.results)
            }
            is AppState.SuccessUpcomingMovies -> {
                showLoading(false)
                Thread.sleep(1000)
                adapterSecond.setMovie(appState.upcomingMovies.results)
            }
            is AppState.Loading -> {
                showLoading(true)
            }
            is AppState.Error -> {
                showLoading(false)
                binding.root.snackBarWithAction(
                    R.string.error, Snackbar.LENGTH_INDEFINITE, "reload"
                ) { viewModel.getFilmFromLocalSource() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(connectivityActionBroadcastReceiver)
    }

    private fun showLoading(isShow: Boolean) {
        binding.loadingLayout.isVisible = isShow
        binding.mainLayout.isVisible = !isShow
    }

    override fun onItemClick(movie: MovieDTO) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                DetailsFragment.newInstance(Bundle().apply { putParcelable(BUNDLE_KEY, movie) })
            )
            .addToBackStack("").commit()
    }

}