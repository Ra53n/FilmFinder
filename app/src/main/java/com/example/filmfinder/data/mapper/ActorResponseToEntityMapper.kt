package com.example.filmfinder.data.mapper

import com.example.filmfinder.data.api.response.ActorResponse
import com.example.filmfinder.domain.model.ActorEntity

class ActorResponseToEntityMapper {
    fun map(actor: ActorResponse): ActorEntity {
        return with(actor) {
            ActorEntity(
                id = id,
                image = image.orEmpty(),
                name = name.orEmpty(),
                asCharacter = asCharacter.orEmpty()
            )
        }
    }
}