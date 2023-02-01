package com.example.filmfinder.data.api.response

import com.google.gson.annotations.SerializedName

class MovieResponse(
    val items: List<MovieItem>,
    val errorMessage: String? = null
)

data class MovieItem(
    val id: String,
    val title: String? = null,
    val fullTitle: String? = null,
    val year: String? = null,
    val releaseState: String? = null,
    val image: String? = null,
    val runtimeStr: String? = null,
    val plot: String? = null,
    val contentRating: String? = null,

    @SerializedName("imDbRating")
    val imDBRating: String? = null,

    @SerializedName("imDbRatingCount")
    val imDBRatingCount: String? = null,

    val metacriticRating: String? = null,
    val genres: String? = null,
    val directors: String? = null,
    val stars: String? = null,
    val actorResponseList: List<ActorResponse>? = null,
)

data class ActorResponse (
    val id: String,
    val image: String? = null,
    val name: String? = null,
    val asCharacter: String? = null
)
