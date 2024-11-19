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
import com.ignacio.rickandmorty.kotlin_utils.extensions.recoverIfPossible
import com.ignacio.rickandmorty.network.ConnectivityMonitor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.path
import javax.inject.Inject

class RealRickAndMortyApi @Inject constructor(
    private val client: HttpClient,
    private val connectivityMonitor: ConnectivityMonitor,
) : RickAndMortyApi {
    override suspend fun getCharacters(
        page: Int,
        query: CharacterQueryCriteria
    ): Result<RMCharacters> {
        var status: HttpStatusCode? = null
        return runCatching<RMCharacters> {
            val response = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = NetworkConstants.RICK_AND_MORTY_HOST
                    path(
                        NetworkConstants.RICK_AND_MORTY_BASE_PATH,
                        NetworkConstants.RICK_AND_MORTY_CHARACTERS_PATH
                    )
                    parameter(key = PARAM_PAGE, value = page)
                    if (query.name.isNotEmpty()) {
                        parameter(key = PARAM_NAME, value = query.name)
                    }
                    if (query.type.isNotEmpty()) {
                        parameter(key = PARAM_TYPE, value = query.type)
                    }
                    if (query.species.isNotEmpty()) {
                        parameter(key = PARAM_SPECIES, value = query.species)
                    }
                    if (query.status != CharacterQueryCriteria.Status.ANY) {
                        parameter(key = PARAM_STATUS, value = query.status.name)
                    }
                    if (query.gender != CharacterQueryCriteria.Gender.ANY) {
                        parameter(key = PARAM_GENDER, value = query.gender.name)
                    }
                }
            }
            status = response.status
            response.body<RMCharactersResponse>().toRMCharacters()
        }.recoverIfPossible { _, result ->
            if (!connectivityMonitor.isNetworkConnected) {
                Result.success(RMCharacters.NoResults)
            } else result
        }.mapError { throwable ->
            NetworkException(status = status, cause = throwable)
        }.onFailure {
            if (BuildConfig.DEBUG) {
                it.printStackTrace()
            }
        }
    }

    companion object {
        const val PARAM_PAGE = "page"
        const val PARAM_NAME = "name"
        const val PARAM_TYPE = "type"
        const val PARAM_SPECIES = "species"
        const val PARAM_STATUS = "status"
        const val PARAM_GENDER = "gender"
    }
}
