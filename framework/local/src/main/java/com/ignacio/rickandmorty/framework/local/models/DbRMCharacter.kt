package com.ignacio.rickandmorty.framework.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignacio.rickandmorty.data.models.LocalRMCharacter

@Entity(tableName = "rickAndMortyCharacters")
data class DbRMCharacter(
    override val created: String,
    override val episode: List<String>,
    override val gender: String,
    @PrimaryKey
    override val id: Int,
    override val image: String?,
    override val name: String,
    override val species: String,
    override val status: String,
    override val type: String?,
    override val url: String,
): LocalRMCharacter
