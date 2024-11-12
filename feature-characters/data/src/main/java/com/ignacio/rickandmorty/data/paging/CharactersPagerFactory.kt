package com.ignacio.rickandmorty.data.paging

import androidx.paging.Pager
import com.ignacio.rickandmorty.data.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.models.LocalRMCharacter

interface CharactersPagerFactory {
    fun create(query: CharacterQueryCriteria): Pager<Int, LocalRMCharacter>
}