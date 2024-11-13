package com.ignacio.rickandmorty.data.paging.pager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.kotlin_utils.paging.PagerContract
import com.ignacio.rickandmorty.paging.mediator.MediatorFactory
import com.ignacio.rickandmorty.paging.pager.AndroidPager
import com.ignacio.rickandmorty.paging.pager.PagerFactory
import javax.inject.Inject

private const val DB_PAGE_SIZE = 40

class RealCharactersPagerFactory @Inject constructor(
    private val mediatorFactory: MediatorFactory<Int, LocalRMCharacter>,
) : PagerFactory<Int, LocalRMCharacter> {
    @OptIn(ExperimentalPagingApi::class)
    override fun create(
        updateFromRemote: suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>,
        pagingSourceFactory: () -> PagingSource<Int, LocalRMCharacter>,
    ): PagerContract<Int, LocalRMCharacter> = AndroidPager(
        Pager(
            config = PagingConfig(pageSize = DB_PAGE_SIZE),
            remoteMediator = mediatorFactory.create(updateFromRemote),
            pagingSourceFactory = pagingSourceFactory,
        )
    )
}