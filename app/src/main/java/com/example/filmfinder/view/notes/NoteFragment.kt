package com.example.filmfinder.view.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmfinder.R
import com.example.filmfinder.data.AppState
import com.example.filmfinder.data.room.movieNotes.MovieNotesEntity
import com.example.filmfinder.databinding.NoteFragmentBinding
import com.example.filmfinder.view.snackBarWithAction
import com.example.filmfinder.viewModel.NotesViewModel
import com.google.android.material.snackbar.Snackbar

class NoteFragment : Fragment() {
    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NoteFragmentAdapter

    companion object {
        fun newInstance() = NoteFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this)[NotesViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        val adapterController = object : NoteFragmentAdapter.Controller {
            override fun onDeleteButtonClick(entity: MovieNotesEntity) {
                viewModel.deleteNoteFromLocalRepository(entity)
            }
        }
        adapter = NoteFragmentAdapter(adapterController)
        binding.noteFragmentRv.layoutManager = LinearLayoutManager(requireContext())
        binding.noteFragmentRv.adapter = adapter
        viewModel.getNotesFromLocalSource()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> binding.root.snackBarWithAction(
                R.string.error, Snackbar.LENGTH_INDEFINITE, "reload"
            ) { viewModel.getNotesFromLocalSource() }
            is AppState.SuccessNotes -> {
                with(binding) {
                    adapter.setMovie(appState.notes)
                }
            }
        }
    }

}