package com.ignacio.rickandmorty.data.paging

import androidx.paging.Pager
import com.ignacio.rickandmorty.data.models.LocalRMCharacter

interface CharactersPagerFactory {
    fun create(query: String): Pager<Int, LocalRMCharacter>
}