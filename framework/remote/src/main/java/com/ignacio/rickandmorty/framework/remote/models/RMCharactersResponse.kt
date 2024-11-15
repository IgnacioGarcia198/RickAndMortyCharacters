package com.ignacio.rickandmorty.framework.remote.models

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable(with = RMCharactersResponse.RMCharactersResponseSerializer::class)
sealed interface RMCharactersResponse {
    @Serializable
    data class Info(
        val count: Int,
        val next: String? = null,
        val pages: Int,
        val prev: String? = null,
    )

    @Serializable
    data class RMCharacter(
        val created: String,
        val episode: List<String>,
        val gender: String,
        val id: Int,
        val image: String? = null,
        val location: RMLinkTrait,
        val name: String,
        val origin: RMLinkTrait,
        val species: String,
        val status: String,
        val type: String? = null,
        val url: String,
    )

    @Serializable
    data class Success(
        val info: Info,
        @SerialName("results")
        val characters: List<RMCharacter>,
    ) : RMCharactersResponse

    @Serializable
    data class Error(
        val error: String,
    ) : RMCharactersResponse

    object RMCharactersResponseSerializer :
        JsonContentPolymorphicSerializer<RMCharactersResponse>(RMCharactersResponse::class) {
        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<RMCharactersResponse> {
            return when {
                "info" in element.jsonObject -> Success.serializer()
                "error" in element.jsonObject -> Error.serializer()
                else -> throw SerializationException("Unknown type")
            }
        }
    }
}
