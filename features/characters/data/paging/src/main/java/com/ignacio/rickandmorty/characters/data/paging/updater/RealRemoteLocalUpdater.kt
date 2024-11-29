package com.ignacio.rickandmorty.characters.data.paging.updater

import com.ignacio.rickandmorty.characters.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.characters.data.paging.datasource.remote.RickAndMortyApi
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.RMCharacters
import javax.inject.Inject

class RealRemoteLocalUpdater @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val charactersLocalDataSource: CharactersLocalPagingDataSource,
): RemoteLocalUpdater<CharacterQueryCriteria, Int> {
    override suspend fun updateFromRemote(
        query: CharacterQueryCriteria,
        page: Int,
        shouldClearLocalCache: Boolean,
    ): Result<Boolean> {
        // Suspending network load via Ktor. This doesn't need to be
        // wrapped in a withContext(Dispatcher.IO) { ... } block since
        // Ktor does it automatically.
        return rickAndMortyApi.getCharacters(page = page, query = query)
            .mapCatching { response ->
                when (response) {
                    is RMCharacters.Characters -> {
                        charactersLocalDataSource.upsertAll(
                            response.characters,
                            clear = shouldClearLocalCache,
                            query = query
                        )
                        !response.hasNextPage
                    }
                    RMCharacters.NoResults -> true
                }
            }
    }
}
