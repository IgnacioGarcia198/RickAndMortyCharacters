package com.ignacio.rickandmorty.presentation.character.list.viewmodel

import androidx.paging.PagingData
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.presentation.character.models.UiRMCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RMCharactersViewModelContract {
    val pagingDataFlow: Flow<PagingData<UiRMCharacter>>
    val query: StateFlow<CharacterListQueryCriteria>

    fun justNameQuery(name: String)
    fun updateName(name: String)
    fun updateType(type: String)
    fun updateSpecies(species: String)
    fun updateStatus(status: CharacterListQueryCriteria.Status)
    fun updateGender(gender: CharacterListQueryCriteria.Gender)
    fun clearQuery()
}