package com.example.filmfinder.data.mapper

import com.example.filmfinder.data.api.response.MovieItem
import com.example.filmfinder.data.api.response.MovieResponse
import com.example.filmfinder.domain.model.MovieEntity

class MovieResponseToEntityMapper(
    private val actorMapper: ActorResponseToEntityMapper
) {

    fun map(response: MovieResponse): List<MovieEntity> {
        return response.items.map {
            MovieEntity(
                id = it.id,
                title = it.title.orEmpty(),
                fullTitle = it.fullTitle,
                year = it.year,
                image = it.image,
                runtimeStr = it.runtimeStr,
                plot = it.plot,
                contentRating = it.contentRating,
                imDBRating = it.imDBRating,
                imDBRatingCount = it.imDBRatingCount,
                genres = it.genres,
                directors = it.directors,
                stars = it.stars,
                actorList = it.actorResponseList?.map { actor -> actorMapper.map(actor) },
            )
        }
    }

    fun map(response: MovieItem): MovieEntity{
        return with(response){
            MovieEntity(
                id = id,
                title = title.orEmpty(),
                fullTitle = fullTitle,
                year = year,
                image = image,
                runtimeStr = runtimeStr,
                plot = plot,
                contentRating = contentRating,
                imDBRating = imDBRating,
                imDBRatingCount = imDBRatingCount,
                genres = genres,
                directors = directors,
                stars = stars,
                actorList = actorResponseList?.map { actor -> actorMapper.map(actor) },
            )
        }
    }
}