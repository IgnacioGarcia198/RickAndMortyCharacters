package com.ignacio.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.ignacio.rickandmorty.data.mediator.CharactersMediatorFactory
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import javax.inject.Inject

private const val DB_PAGE_SIZE = 40

class RealCharactersPagerFactory @Inject constructor(
    private val mediatorFactory: CharactersMediatorFactory,
) : CharactersPagerFactory<Int, LocalRMCharacter> {
    @OptIn(ExperimentalPagingApi::class)
    override fun create(
        updateFromRemote: suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>,
        pagingSourceFactory: () -> PagingSource<Int, LocalRMCharacter>,
    ): Pager<Int, LocalRMCharacter> = Pager(
        config = PagingConfig(pageSize = DB_PAGE_SIZE),
        remoteMediator = mediatorFactory.create(updateFromRemote),
        pagingSourceFactory = pagingSourceFactory,
    )
}