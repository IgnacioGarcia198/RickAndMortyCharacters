package com.ignacio.rickandmorty.data.paging

import androidx.paging.Pager
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria

interface CharactersPagerFactory {
    fun create(query: CharacterListQueryCriteria): Pager<Int, LocalRMCharacter>
}