package com.example.filmfinder.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmfinder.R
import com.example.filmfinder.databinding.LikedMoviesFragmentBinding
import com.example.filmfinder.presentation.adapter.LikedMoviesFragmentAdapter
import com.example.filmfinder.presentation.model.event.LikedMoviesEvent
import com.example.filmfinder.presentation.viewModel.LikedMoviesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LikedMoviesFragment : Fragment(R.layout.liked_movies_fragment) {
    private val binding: LikedMoviesFragmentBinding by viewBinding()
    private lateinit var adapter: LikedMoviesFragmentAdapter
    private lateinit var mOptionsMenu: Menu

    private val viewModel: LikedMoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        bindAdapter()
    }

    private fun bindAdapter() {
        adapter = LikedMoviesFragmentAdapter { movie ->
            viewModel.sendEvent(
                LikedMoviesEvent.DeleteMovieClick(movie)
            )
        }
        binding.likedMoviesRv.adapter = adapter
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.liked_fragment_menu, menu)
        mOptionsMenu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortByRating -> {
                viewModel.sendEvent(LikedMoviesEvent.SortMoviesByRatingClick)
                viewModel.switchSortingIcon(item)
                mOptionsMenu.findItem(R.id.clearFilters).isVisible = true
            }
            R.id.clearFilters -> {
                mOptionsMenu.findItem(R.id.sortByRating).icon = null
                viewModel.sendEvent(LikedMoviesEvent.ClearFiltersClick)
                item.isVisible = false
            }
        }
        return true
    }

    private fun switchSortByRatingFilterIcon(sortingAsc: Boolean) {
        val menuItem = mOptionsMenu.findItem(R.id.clearFilters)
        menuItem.setIcon(
            if (sortingAsc) {
                R.drawable.ic_baseline_keyboard_arrow_up_24
            } else {
                R.drawable.ic_baseline_keyboard_arrow_down_24
            }
        )
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewStateObserve.collect { state ->
                state.movies?.let { adapter.setMovie(it) }
                if (state.showError) {
                    showError()
                }
                state.isSortMoviesAsc?.let { switchSortByRatingFilterIcon(it) }
            }
        }
    }

    private fun showError() {
        binding.root.snackBarWithAction(
            R.string.error,
            Snackbar.LENGTH_INDEFINITE,
            resources.getString(R.string.reload)
        ) {
            viewModel.loadLikedMovies()
        }
    }
}