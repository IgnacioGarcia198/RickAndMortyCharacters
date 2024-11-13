package com.ignacio.rickandmorty.data.paging.updater

import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria

interface RemoteLocalUpdater {
    /**
     * Returns Result.success with whether the remote service arrived to the end of its pagination
     * or Result.failure with a throwable if something failed.
     */
    suspend fun updateFromRemote(
        query: CharacterQueryCriteria,
        page: Int,
        shouldClearLocalCache: Boolean,
    ): Result<Boolean>
}