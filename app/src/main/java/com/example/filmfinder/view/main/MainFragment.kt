package com.example.filmfinder.view.main

import android.content.Context
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

const val SWITCH_IS_CHECKED = "SWITCH_IS_CHECKED"

class MainFragment : Fragment(), OnItemClickListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter(this)
    private val adapterSecond = MainFragmentAdapter(this)
    private val connectivityActionBroadcastReceiver = ConnectivityActionBroadcastReceiver()
    private val isAdult: Boolean by lazy {
        requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(SWITCH_IS_CHECKED, false)
    }


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
        requireActivity().registerReceiver(
            connectivityActionBroadcastReceiver, IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
            )
        )
        bindViews()
        loadContent()
    }

    private fun loadContent() {
        if (isAdult) {
            viewModel.getMoviesWitchAdultFromServer()
        } else {
            viewModel.getMoviesFromServer()
        }
    }

    private fun bindViews() {
        binding.mainFragmentSwitchAdult.isChecked = isAdult
        bindAdapters()
        bindSwitchAdultClickListener()
    }

    private fun bindSwitchAdultClickListener() {
        binding.mainFragmentSwitchAdult.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.getMoviesWitchAdultFromServer()
            } else {
                viewModel.getMoviesFromServer()
            }
            val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPreferences?.let {
                it.edit().apply { putBoolean("SWITCH_IS_CHECKED", isChecked) }.apply()
            }
        }
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
        }
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessPopularMovies -> {
                showLoading(false)
                adapter.setMovie(appState.popularMovies.results)
            }
            is AppState.SuccessUpcomingMovies -> {
                showLoading(false)
                adapterSecond.setMovie(appState.upcomingMovies.results)
            }
            is AppState.Loading -> {
                showLoading(true)
            }
            is AppState.Error -> {
                showLoading(false)
                binding.root.snackBarWithAction(
                    R.string.error, Snackbar.LENGTH_INDEFINITE, "reload"
                ) { viewModel.getMoviesFromServer() }
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