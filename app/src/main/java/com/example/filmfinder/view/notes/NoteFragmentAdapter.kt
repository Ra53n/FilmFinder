package com.example.filmfinder.view.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.data.room.movieNotes.MovieNotesEntity

class NoteFragmentAdapter(private val controller: Controller) :
    RecyclerView.Adapter<NoteFragmentAdapter.NotesViewHolder>() {
    private var movieData: List<MovieNotesEntity> = listOf()

    interface Controller {
        fun onDeleteButtonClick(entity: MovieNotesEntity)
    }

    fun setMovie(data: List<MovieNotesEntity>) {
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
        fun bind(entity: MovieNotesEntity) {
            with(itemView) {
                findViewById<TextView>(R.id.item_note__name_textview).text =
                    entity.title
                findViewById<TextView>(R.id.item_note__content_textview).text =
                    entity.note
                findViewById<TextView>(R.id.item_note__date_textview).text = entity.date
                findViewById<ImageView>(R.id.item_note__poster_iv)
                    .load("${resources.getString(R.string.film_poster_endpoint)}${entity.posterPath}")
                findViewById<Button>(R.id.item_note__delete_button).setOnClickListener {
                    controller.onDeleteButtonClick(
                        entity
                    )
                }
            }
        }
    }
}