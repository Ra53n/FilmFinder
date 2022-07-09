package com.example.filmfinder.view.likedMovies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.room.likedMovies.LikedMoviesEntity
import com.example.filmfinder.databinding.LikedMoviesFragmentBinding
import com.example.filmfinder.view.snackBarWithAction
import com.example.filmfinder.viewModel.LikedMoviesViewModel
import com.google.android.material.snackbar.Snackbar

class LikedMoviesFragment : Fragment(R.layout.liked_movies_fragment) {
    private val binding: LikedMoviesFragmentBinding by viewBinding()
    private lateinit var adapter: LikedMoviesFragmentAdapter
    private lateinit var mOptionsMenu: Menu

    companion object {
        fun newInstance() = LikedMoviesFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this)[LikedMoviesViewModel::class.java] }

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
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
        bindAdapter()
        viewModel.getLikedMoviesFromLocalSource()
    }

    private fun bindAdapter() {
        val controller = object : LikedMoviesFragmentAdapter.Controller {
            override fun onDeleteItemClick(entity: LikedMoviesEntity) {
                viewModel.deleteNoteFromLocalRepository(entity)
            }
        }
        adapter = LikedMoviesFragmentAdapter(controller)
        binding.likedMoviesRv.layoutManager = LinearLayoutManager(requireContext())
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
            R.id.liked_movies_fragment__sort_by_rating -> {
                viewModel.getSortedLikedMoviesByRating()
                viewModel.switchSortingIcon(item)
                mOptionsMenu.findItem(R.id.liked_movies_fragment__clear_filters).isVisible = true
            }
            R.id.liked_movies_fragment__clear_filters -> {
                mOptionsMenu.findItem(R.id.liked_movies_fragment__sort_by_rating).icon = null
                viewModel.getLikedMoviesFromLocalSource()
                item.isVisible = false
            }
        }
        return true
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> binding.root.snackBarWithAction(
                R.string.error, Snackbar.LENGTH_INDEFINITE, "reload"
            ) { viewModel.getLikedMoviesFromLocalSource() }
            is AppState.Success -> {
                with(binding) {
                    adapter.setMovie(appState.likedMovies)
                }
            }
        }
    }
}