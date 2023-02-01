package com.example.filmfinder.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmfinder.R
import com.example.filmfinder.databinding.NoteFragmentBinding
import com.example.filmfinder.presentation.adapter.NoteFragmentAdapter
import com.example.filmfinder.presentation.model.event.NoteEvent
import com.example.filmfinder.presentation.viewModel.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class NoteFragment : Fragment(R.layout.note_fragment) {
    private val binding: NoteFragmentBinding by viewBinding()
    private lateinit var adapter: NoteFragmentAdapter

    private val viewModel: NotesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeUiState()
        viewModel.getNotes()
    }

    private fun initAdapter() {
        adapter = NoteFragmentAdapter { movie -> viewModel.sendEvent(NoteEvent.MovieDelete(movie)) }
        binding.recyclerView.adapter = adapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewStateObserve.collect { state ->
                adapter.setMovies(state.notes)
                if (state.showError) {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        binding.root.snackBarWithAction(
            R.string.error,
            Snackbar.LENGTH_INDEFINITE,
            resources.getString(R.string.reload)
        ) {
            viewModel.getNotes()
        }
    }

    companion object {
        fun newInstance() = NoteFragment()
    }
}