package com.example.filmfinder.view.notes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.filmfinder.App
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.data.room.movieNotes.MovieNotesEntity
import com.example.filmfinder.databinding.NoteAddFragmentBinding
import java.util.*


const val NOTE_ADD_BUNDLE_KEY = "NOTE_ADD_BUNDLE_KEY"

class NoteAddFragment : Fragment() {
    private var _binding: NoteAddFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle) = NoteAddFragment().apply { arguments = bundle }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteAddFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieDTO = arguments?.getParcelable<MovieDTO>(NOTE_ADD_BUNDLE_KEY)
        movieDTO?.let { setData(it) }
        binding.noteAddFragmentAddButton.setOnClickListener {
            Thread {
                movieDTO?.let {
                    App.getMovieNotesDao().insert(createNote(it))
                }
            }.start()
            Handler(Looper.getMainLooper()).postDelayed({activity?.supportFragmentManager?.popBackStack()},100)
        }
    }

    private fun setData(movieDTO: MovieDTO) {
        with(binding) {
            noteAddFragmentTitleTv.text = movieDTO.title
            noteAddFragmentDescriptionTv.text = movieDTO.overview
            noteAddFragmentDescriptionTv.movementMethod = ScrollingMovementMethod()
            noteAddFragmentIv.load("https://www.themoviedb.org/t/p/original" + movieDTO.posterPath)
            noteAddFragmentDateTv.text = Date(System.currentTimeMillis()).toString()
        }
    }

    private fun createNote(movieDTO: MovieDTO): MovieNotesEntity {
        return with(binding) {
            MovieNotesEntity(
                0,
                movieDTO.id,
                movieDTO.title,
                noteAddFragmentNoteTv.text.toString(),
                movieDTO.posterPath,
                noteAddFragmentDateTv.text.toString()
            )
        }
    }
}