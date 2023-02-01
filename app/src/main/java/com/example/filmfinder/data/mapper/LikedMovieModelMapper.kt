package com.example.filmfinder.data.mapper

import com.example.filmfinder.data.room.model.LikedMoviesModel
import com.example.filmfinder.domain.model.MovieEntity

class LikedMovieModelMapper {
    fun map(model: LikedMoviesModel): MovieEntity {
        return with(model) {
            MovieEntity(
                id = movieId,
                title = title,
                plot = plot,
                imDBRating = rating,
                year = realiseDate,
                image = posterPath
            )
        }
    }

    fun map(entity: MovieEntity): LikedMoviesModel {
        return with(entity) {
            LikedMoviesModel(
                movieId = id,
                title = title,
                plot = plot.orEmpty(),
                rating = imDBRating.orEmpty(),
                realiseDate = year.orEmpty(),
                posterPath = image.orEmpty()
            )
        }
    }
}