package com.example.filmfinder.view.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.room.movieNotes.MovieNotesEntity
import com.example.filmfinder.databinding.NoteFragmentBinding
import com.example.filmfinder.view.snackBarWithAction
import com.example.filmfinder.viewModel.NotesViewModel
import com.google.android.material.snackbar.Snackbar

class NoteFragment : Fragment(R.layout.note_fragment) {
    private val binding: NoteFragmentBinding by viewBinding()
    private lateinit var adapter: NoteFragmentAdapter

    companion object {
        fun newInstance() = NoteFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this)[NotesViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
        initAdapter()
        viewModel.getNotesFromLocalSource()
    }

    private fun initAdapter() {
        val adapterController = object : NoteFragmentAdapter.Controller {
            override fun onDeleteButtonClick(entity: MovieNotesEntity) {
                viewModel.deleteNoteFromLocalRepository(entity)
            }
        }
        adapter = NoteFragmentAdapter(adapterController)
        binding.noteFragmentRv.layoutManager = LinearLayoutManager(requireContext())
        binding.noteFragmentRv.adapter = adapter
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> binding.root.snackBarWithAction(
                R.string.error, Snackbar.LENGTH_INDEFINITE, resources.getString(R.string.reload)
            ) { viewModel.getNotesFromLocalSource() }
            is AppState.SuccessNotes -> {
                with(binding) {
                    adapter.setMovie(appState.notes)
                }
            }
        }
    }
}