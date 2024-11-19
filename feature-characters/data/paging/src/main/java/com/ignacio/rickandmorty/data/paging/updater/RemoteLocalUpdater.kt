package com.ignacio.rickandmorty.data.paging.updater

interface RemoteLocalUpdater<Query : Any, Key : Any> {
    /**
     * Returns Result.success with whether the remote service arrived to the end of its pagination
     * or Result.failure with a throwable if something failed.
     */
    suspend fun updateFromRemote(
        query: Query,
        page: Key,
        shouldClearLocalCache: Boolean,
    ): Result<Boolean>
}