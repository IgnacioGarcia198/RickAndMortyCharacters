package com.ignacio.rickandmorty.framework.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
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
) : LocalRMCharacter {
    companion object {
        val dummy = DbRMCharacter(
            created = "created",
            episode = emptyList(),
            gender = "male",
            id = 1,
            image = null,
            name = "name",
            species = "species",
            status = "status",
            type = "type",
            url = "url"
        )
        const val TABLE_NAME = "rickAndMortyCharacters"
        const val NAME_COLUMN = "name"
        const val SPECIES_COLUMN = "species"
        const val TYPE_COLUMN = "type"
        const val STATUS_COLUMN = "status"
        const val GENDER_COLUMN = "gender"
    }
}
