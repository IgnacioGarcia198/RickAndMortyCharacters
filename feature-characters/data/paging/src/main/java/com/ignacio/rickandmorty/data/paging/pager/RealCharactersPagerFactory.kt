package com.ignacio.rickandmorty.data.paging.pager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ignacio.rickandmorty.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.kotlin_utils.paging.PagerContract
import com.ignacio.rickandmorty.paging.mediator.MediatorFactory
import com.ignacio.rickandmorty.paging.pager.AndroidPager
import com.ignacio.rickandmorty.paging.pager.PagerFactory
import javax.inject.Inject

class RealCharactersPagerFactory @Inject constructor(
    private val mediatorFactory: MediatorFactory<Int, LocalRMCharacter, CharacterQueryCriteria>,
    private val localPagingDataSource: CharactersLocalPagingDataSource,
) : PagerFactory<Int, LocalRMCharacter, CharacterQueryCriteria> {
    @OptIn(ExperimentalPagingApi::class)
    override fun create(
        queryCriteria: CharacterQueryCriteria,
    ): PagerContract<Int, LocalRMCharacter> = AndroidPager(
        Pager(
            config = PagingConfig(pageSize = DB_PAGE_SIZE),
            remoteMediator = mediatorFactory.create(queryCriteria),
            pagingSourceFactory = { localPagingDataSource.getRMCharacters(queryCriteria) },
        )
    )

    companion object {
        const val DB_PAGE_SIZE = 40
    }
}
