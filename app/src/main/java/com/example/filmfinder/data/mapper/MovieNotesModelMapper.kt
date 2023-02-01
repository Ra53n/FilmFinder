package com.example.filmfinder.data.mapper

import com.example.filmfinder.data.room.model.MovieNotesModel
import com.example.filmfinder.presentation.model.MovieNoteUiModel

class MovieNotesModelMapper {
    fun map(model: MovieNotesModel): MovieNoteUiModel {
        return with(model) {
            MovieNoteUiModel(
                id = id,
                title = title,
                note = note,
                date = date,
                posterPath = posterPath
            )
        }
    }

    fun map(entity: MovieNoteUiModel): MovieNotesModel {
        return with(entity) {
            MovieNotesModel(
                id = id,
                title = title,
                note = note,
                date = date,
                posterPath = posterPath
            )
        }
    }
}