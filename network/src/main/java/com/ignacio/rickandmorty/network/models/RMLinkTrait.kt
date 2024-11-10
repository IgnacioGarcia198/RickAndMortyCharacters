package com.ignacio.rickandmorty.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RMLinkTrait(
    val name: String = "unknown",
    val url: String? = null,
)