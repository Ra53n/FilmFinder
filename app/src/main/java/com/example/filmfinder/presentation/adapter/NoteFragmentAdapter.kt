package com.example.filmfinder.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.databinding.NoteRecyclerItemBinding
import com.example.filmfinder.presentation.model.MovieNoteUiModel

class NoteFragmentAdapter(
    private val onDeleteMovieClick: (MovieNoteUiModel) -> Unit
) : RecyclerView.Adapter<NoteFragmentAdapter.NotesViewHolder>() {

    private var movieData: List<MovieNoteUiModel> = listOf()

    fun setMovies(data: List<MovieNoteUiModel>) {
        this.movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.note_recycler_item, parent, false)
    )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(this.movieData[position])
    }

    override fun getItemCount() = movieData.size


    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = NoteRecyclerItemBinding.bind(itemView)
        fun bind(entity: MovieNoteUiModel) {
            binding.movieTitle.text = entity.title
            binding.noteContent.text = entity.note
            binding.noteDate.text = entity.date
            binding.movieImage.load(entity.posterPath)
            binding.noteDeleteButton.setOnClickListener { onDeleteMovieClick(entity) }
        }
    }
}