package com.ignacio.rickandmorty.framework.remote.api

import com.ignacio.rickandmorty.data.paging.datasource.remote.RickAndMortyApi
import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.paging.models.RMCharacters
import com.ignacio.rickandmorty.framework.remote.constants.NetworkConstants
import com.ignacio.rickandmorty.framework.remote.exceptions.NetworkException
import com.ignacio.rickandmorty.framework.remote.mapping.toRMCharacters
import com.ignacio.rickandmorty.framework.remote.models.RMCharactersResponse
import com.ignacio.rickandmorty.kotlin_utils.build_config.BuildConfig
import com.ignacio.rickandmorty.kotlin_utils.extensions.mapError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path
import javax.inject.Inject

class RealRickAndMortyApi @Inject constructor(
    private val client: HttpClient
) : RickAndMortyApi {
    override suspend fun getCharacters(
        page: Int,
        query: CharacterQueryCriteria
    ): Result<RMCharacters> {
        return runCatching<RMCharacters> {
            val response = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = NetworkConstants.RICK_AND_MORTY_HOST
                    path(
                        NetworkConstants.RICK_AND_MORTY_BASE_PATH,
                        NetworkConstants.RICK_AND_MORTY_CHARACTERS_PATH
                    )
                    parameter(key = "page", value = page) // TODO: Extract constants for params
                    if (query.name.isNotEmpty()) {
                        parameter(key = "name", value = query.name)
                    }
                    if (query.type.isNotEmpty()) {
                        parameter(key = "type", value = query.type)
                    }
                    if (query.species.isNotEmpty()) {
                        parameter(key = "species", value = query.species)
                    }
                    if (query.status != CharacterQueryCriteria.Status.ANY) {
                        parameter(key = "status", value = query.status.name)
                    }
                    if (query.gender != CharacterQueryCriteria.Gender.ANY) {
                        parameter(key = "gender", value = query.gender.name)
                    }
                }
            }
            if (!response.status.isSuccess()) {
                throw NetworkException(
                    errorCode = response.status.value,
                    errorDescription = response.status.description
                )
            }
            response.body<RMCharactersResponse>().toRMCharacters()
        }.mapError { throwable ->
            when (throwable) {
                is NetworkException -> throwable
                else -> NetworkException(cause = throwable)
            }
        }.onFailure {
            if (BuildConfig.DEBUG) {
                it.printStackTrace()
            }
        }
    }
}
