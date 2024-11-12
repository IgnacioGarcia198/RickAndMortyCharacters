package com.ignacio.rickandmorty.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.datasources.remote.RickAndMortyApi
import com.ignacio.rickandmorty.data.models.LocalRMCharacter

private const val CHARACTERS_API_FIRST_PAGE = 1
private const val CHARACTERS_API_RESULTS_PER_PAGE = 20

@OptIn(ExperimentalPagingApi::class)
class RMCharactersMediator(
    private val query: String,
    private val localDataSource: CharactersLocalDataSource,
    private val networkService: RickAndMortyApi,
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
        return try {
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

            // Suspending network load via Ktor. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Ktor does it automatically.
            val response = networkService.getCharacters(page = loadKey, query = query)
                .getOrElse { return MediatorResult.Error(it) }

            localDataSource.upsertAll(
                response.characters,
                clear = loadType == LoadType.REFRESH,
                query = query
            )

            MediatorResult.Success(
                endOfPaginationReached = !response.hasNextPage // response.characters.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        var initialized = false
    }
}