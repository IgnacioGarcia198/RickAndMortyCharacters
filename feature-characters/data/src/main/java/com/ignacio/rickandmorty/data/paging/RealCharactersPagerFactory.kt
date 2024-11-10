package com.ignacio.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.mediator.CharactersMediatorFactory
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import javax.inject.Inject

private const val DB_PAGE_SIZE = 40

class RealCharactersPagerFactory @Inject constructor(
    private val mediatorFactory: CharactersMediatorFactory,
    private val charactersLocalDataSource: CharactersLocalDataSource,
): CharactersPagerFactory {
    @OptIn(ExperimentalPagingApi::class)
    override fun create(query: String): Pager<Int, LocalRMCharacter> = Pager(
        config = PagingConfig(pageSize = DB_PAGE_SIZE),
        remoteMediator = mediatorFactory.create(query),
        pagingSourceFactory = { charactersLocalDataSource.getRMCharacters(query) }
    )
}