package com.example.filmfinder.view.notes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.api.load
import com.example.filmfinder.App
import com.example.filmfinder.R
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.data.room.movieNotes.MovieNotesEntity
import com.example.filmfinder.databinding.NoteAddFragmentBinding
import java.util.*


const val NOTE_ADD_BUNDLE_KEY = "NOTE_ADD_BUNDLE_KEY"

class NoteAddFragment : Fragment(R.layout.note_add_fragment) {
    private val binding: NoteAddFragmentBinding by viewBinding()

    private var movieDTO: MovieDTO? = null

    companion object {
        fun newInstance(bundle: Bundle) = NoteAddFragment().apply { arguments = bundle }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.movieDTO = arguments?.getParcelable<MovieDTO>(NOTE_ADD_BUNDLE_KEY)
        setData()
        initListeners()
    }

    private fun initListeners() {
        binding.noteAddFragmentAddButton.setOnClickListener {
            Thread {
                movieDTO?.let {
                    App.getMovieNotesDao().insert(createNote())
                }
            }.start()
            Handler(Looper.getMainLooper()).postDelayed(
                { activity?.supportFragmentManager?.popBackStack() },
                100
            )
        }
    }


    private fun setData() {
        with(binding) {
            movieDTO?.let {
                noteAddFragmentTitleTv.text = it.title
                noteAddFragmentDescriptionTv.text = it.overview
                noteAddFragmentDescriptionTv.movementMethod = ScrollingMovementMethod()
                noteAddFragmentIv.load("${resources.getString(R.string.film_poster_endpoint)}${it.posterPath}")
                noteAddFragmentDateTv.text = Date(System.currentTimeMillis()).toString()
            }
        }
    }

    private fun createNote(): MovieNotesEntity {
        val noteEntity = movieDTO?.let {
            with(binding) {
                MovieNotesEntity(
                    0,
                    it.id,
                    it.title,
                    noteAddFragmentNoteTv.text.toString(),
                    it.posterPath,
                    noteAddFragmentDateTv.text.toString()
                )
            }
        }
        return noteEntity!!
    }
}