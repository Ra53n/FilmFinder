package com.example.filmfinder.presentation.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.databinding.NoteAddFragmentBinding
import com.example.filmfinder.domain.model.MovieEntity
import com.example.filmfinder.presentation.model.event.NoteAddEvent
import com.example.filmfinder.presentation.viewModel.NoteAddViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*


const val NOTE_ADD_BUNDLE_KEY = "NOTE_ADD_BUNDLE_KEY"

class NoteAddFragment : Fragment(R.layout.note_add_fragment) {
    private val binding: NoteAddFragmentBinding by viewBinding()

    private val viewModel: NoteAddViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        initListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(module {
            single(named("noteAddMovie")) {
                arguments?.getSerializable(NOTE_ADD_BUNDLE_KEY) as MovieEntity
            }
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initListeners() {
        binding.addButton.setOnClickListener {
            viewModel.sendEvent(NoteAddEvent.AddMovieClick(binding.note.text.toString()))
        }
    }


    private fun setData(movie: MovieEntity) {
        with(binding) {
            title.text = movie.title
            description.text = movie.plot
            description.movementMethod = ScrollingMovementMethod()
            image.load(movie.image)
            date.text = Date(System.currentTimeMillis()).toString()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewStateObserve.collect { state ->
                state.movie?.let {
                    setData(it)
                }
                if (state.showError) {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
    }
}