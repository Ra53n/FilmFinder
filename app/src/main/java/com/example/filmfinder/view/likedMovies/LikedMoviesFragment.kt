package com.example.filmfinder.view.likedMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.room.likedMovies.LikedMoviesEntity
import com.example.filmfinder.databinding.LikedMoviesFragmentBinding
import com.example.filmfinder.view.snackBarWithAction
import com.example.filmfinder.viewModel.LikedMoviesViewModel
import com.google.android.material.snackbar.Snackbar

class LikedMoviesFragment : Fragment() {
    private var _binding: LikedMoviesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LikedMoviesFragmentAdapter

    companion object {
        fun newInstance() = LikedMoviesFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this)[LikedMoviesViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LikedMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        val controller = object : LikedMoviesFragmentAdapter.Controller {
            override fun onDeleteItemClick(entity: LikedMoviesEntity) {
                viewModel.deleteNoteFromLocalRepository(entity)
            }
        }
        adapter = LikedMoviesFragmentAdapter(controller)
        binding.likedMoviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.likedMoviesRv.adapter = adapter
        viewModel.getLikedMoviesFromLocalSource()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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