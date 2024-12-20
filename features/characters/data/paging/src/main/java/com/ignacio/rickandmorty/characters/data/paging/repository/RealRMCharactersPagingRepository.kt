package com.ignacio.rickandmorty.characters.data.paging.repository

import com.ignacio.rickandmorty.characters.data.paging.mapping.toData
import com.ignacio.rickandmorty.characters.data.paging.mapping.toDomain
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.characters.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.characters.domain.repository.RMCharactersPagingRepository
import com.ignacio.rickandmorty.kotlin_utils.extensions.mapPagedFlow
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import com.ignacio.rickandmorty.paging.pager.PagerFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.ignacio.rickandmorty.characters.domain.models.RMCharacter as DomainCharacter

class RealRMCharactersPagingRepository @Inject constructor(
    private val pagerFactory: PagerFactory<Int, LocalRMCharacter, CharacterQueryCriteria>,
) : RMCharactersPagingRepository {
    override fun getRMCharacters(query: CharacterListQueryCriteria): Flow<PagedData<DomainCharacter>> {
        val dataQuery = query.toData()
        return pagerFactory.create(
            dataQuery
        )
            .flow
            .mapPagedFlow { it.toDomain() }
    }
}