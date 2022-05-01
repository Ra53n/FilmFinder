package com.example.filmfinder.view.main

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmfinder.ConnectivityActionBroadcastReceiver
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.databinding.MainFragmentBinding
import com.example.filmfinder.view.likedMovies.LikedMoviesFragment
import com.example.filmfinder.view.notes.NoteFragment
import com.example.filmfinder.view.snackBarWithAction
import com.example.filmfinder.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

const val SWITCH_IS_CHECKED = "SWITCH_IS_CHECKED"

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val onItemClickListener = MainOnItemClickListener(this)
    private val adapter = MainFragmentAdapter(onItemClickListener)
    private val adapterSecond = MainFragmentAdapter(onItemClickListener)

    private val connectivityActionBroadcastReceiver = ConnectivityActionBroadcastReceiver()

    private val isAdult: Boolean by lazy {
        requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(SWITCH_IS_CHECKED, false)
    }

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }


    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_screen_menu, menu)
        val item = menu.findItem(R.id.menu_item__adult_switch)
            .apply { setActionView(R.layout.main_switch_layout) }
        val switch = item.actionView.findViewById<SwitchCompat>(R.id.main_switch)
        bindSwitchOnCheckedChangedListener(switch)
        switch?.isChecked = isAdult
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun bindSwitchOnCheckedChangedListener(switch: SwitchCompat) {
        switch.setOnCheckedChangeListener { _, isChecked ->
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item__liked_movies -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(
                        R.id.container,
                        LikedMoviesFragment.newInstance()
                    )
                    .addToBackStack("").commit()
                true
            }
            R.id.menu_item__movie_notes -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(
                        R.id.container,
                        NoteFragment.newInstance()
                    )
                    .addToBackStack("").commit()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, (Observer { renderData(it) }))
        activity?.registerReceiver(
            connectivityActionBroadcastReceiver, IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
            )
        )
        bindAdapters()
        loadContent()
    }

    private fun loadContent() {
        if (isAdult) {
            viewModel.getMoviesWitchAdultFromServer()
        } else {
            viewModel.getMoviesFromServer()
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

}