package com.example.filmfinder.domain.model

import java.io.Serializable

class MovieEntity(
    val id: String,
    val title: String,
    val fullTitle: String? = null,
    val year: String? = null,
    val image: String? = null,
    val runtimeStr: String? = null,
    val plot: String? = null,
    val contentRating: String? = null,
    val imDBRating: String? = null,
    val imDBRatingCount: String? = null,
    val genres: String? = null,
    val directors: String? = null,
    val stars: String? = null,
    val actorList: List<ActorEntity>? = null,
) : Serializable

class ActorEntity(
    val id: String,
    val image: String,
    val name: String,
    val asCharacter: String
) : Serializable