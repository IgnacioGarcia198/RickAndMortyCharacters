package com.ignacio.rickandmorty.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ignacio.rickandmorty.data.models.LocalRMCharacter

private const val CHARACTERS_API_FIRST_PAGE = 1
private const val CHARACTERS_API_RESULTS_PER_PAGE = 20

@OptIn(ExperimentalPagingApi::class)
class RMCharactersMediator(
    private val updateFromRemote: suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>,
) : RemoteMediator<Int, LocalRMCharacter>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded.
        return if (shouldRefreshInitially()) {
            initialized = true
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    // TODO: CHECK LAST UPDATE
    private fun shouldRefreshInitially(): Boolean = !initialized

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalRMCharacter>
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> CHARACTERS_API_FIRST_PAGE
            LoadType.PREPEND ->
                return MediatorResult.Success(endOfPaginationReached = true)

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    CHARACTERS_API_FIRST_PAGE
                } else {
                    (lastItem.id / CHARACTERS_API_RESULTS_PER_PAGE) + 1
                }
            }
        }
        return updateFromRemote(loadKey, loadType == LoadType.REFRESH,).toMediatorResult()
    }

    companion object {
        var initialized = false
    }
}

@OptIn(ExperimentalPagingApi::class)
fun Result<Boolean>.toMediatorResult(): RemoteMediator.MediatorResult = when {
    this.isSuccess -> RemoteMediator.MediatorResult.Success(endOfPaginationReached = getOrThrow())
    else -> RemoteMediator.MediatorResult.Error(exceptionOrNull()!!)
}