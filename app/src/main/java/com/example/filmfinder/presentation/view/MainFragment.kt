package com.example.filmfinder.presentation.view

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmfinder.R
import com.example.filmfinder.databinding.MainFragmentBinding
import com.example.filmfinder.presentation.adapter.MainFragmentAdapter
import com.example.filmfinder.presentation.broadcastReciever.ConnectivityActionBroadcastReceiver
import com.example.filmfinder.presentation.broadcastReciever.GeofenceBroadcastReceiver
import com.example.filmfinder.presentation.model.event.MainEvent
import com.example.filmfinder.presentation.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SWITCH_IS_CHECKED = "SWITCH_IS_CHECKED"

class MainFragment : Fragment(R.layout.main_fragment) {

    private val binding: MainFragmentBinding by viewBinding()

    private val popularMoviesAdapter =
        MainFragmentAdapter(
            onMovieClick = { movie -> viewModel.sendEvent(MainEvent.MovieClicked(movie)) },
            onLikeMovieClick = { movie -> viewModel.sendEvent(MainEvent.MovieLiked(movie)) }
        )
    private val upcomingMoviesAdapter =
        MainFragmentAdapter(
            onMovieClick = { movie -> viewModel.sendEvent(MainEvent.MovieClicked(movie)) },
            onLikeMovieClick = { movie -> viewModel.sendEvent(MainEvent.MovieLiked(movie)) }
        )

    private val isAdult: Boolean by lazy {
        requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(SWITCH_IS_CHECKED, false)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_screen_menu, menu)
        val item = menu.findItem(R.id.menu_item__adult_switch)
            .apply { setActionView(R.layout.main_switch_layout) }
        val switch = item.actionView?.findViewById<SwitchCompat>(R.id.main_switch)
            ?.apply { bindSwitchOnCheckedChangedListener(this) }
        switch?.isChecked = isAdult
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun bindSwitchOnCheckedChangedListener(switch: SwitchCompat) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.getMovies(needToShowAdult = isChecked)
            val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPreferences?.let {
                it.edit().apply { putBoolean("SWITCH_IS_CHECKED", isChecked) }.apply()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item__liked_movies -> {
                viewModel.sendEvent(MainEvent.MenuItemLikedMoviesClick)
                true
            }
            R.id.menu_item__movie_notes -> {
                viewModel.sendEvent(MainEvent.MenuItemNotesClick)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        bindAdapters()
        viewModel.getMovies(isAdult)
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewStateObserve.collect { state ->
                showLoading(state.needShowLoading)
                popularMoviesAdapter.setMovies(state.popularMovies)
                upcomingMoviesAdapter.setMovies(state.upcomingMovies)
                if (state.showError) {
                    showError()
                }
            }
        }
    }


    private fun bindAdapters() {
        with(binding) {
            popularMoviesRecyclerView.adapter = popularMoviesAdapter
            upcomingMoviesRecyclerView.adapter = upcomingMoviesAdapter
        }
    }

    private fun showError() {
        binding.root.snackBarWithAction(
            R.string.error, Snackbar.LENGTH_INDEFINITE, resources.getString(R.string.reload)
        ) { viewModel.getMovies(isAdult) }
    }

    private fun showLoading(isShow: Boolean) {
        binding.loadingLayout.isVisible = isShow
        binding.mainLayout.isVisible = !isShow
    }
}