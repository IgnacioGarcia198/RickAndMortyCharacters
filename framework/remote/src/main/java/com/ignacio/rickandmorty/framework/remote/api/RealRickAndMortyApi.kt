package com.ignacio.rickandmorty.framework.remote.api

import com.ignacio.rickandmorty.data.datasources.remote.RickAndMortyApi
import com.ignacio.rickandmorty.data.models.RMCharacters
import com.ignacio.rickandmorty.framework.remote.constants.NetworkConstants
import com.ignacio.rickandmorty.framework.remote.mapping.toRMCharacters
import com.ignacio.rickandmorty.framework.remote.models.RMCharactersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import javax.inject.Inject

class RealRickAndMortyApi @Inject constructor(
    private val client: HttpClient
) : RickAndMortyApi {
    override suspend fun getCharacters(): Result<RMCharacters> {
        return kotlin.runCatching {
            val response = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = NetworkConstants.RICK_AND_MORTY_HOST
                    path(
                        NetworkConstants.RICK_AND_MORTY_BASE_PATH,
                        NetworkConstants.RICK_AND_MORTY_CHARACTERS_PATH
                    )
                }
            }
            response.body<RMCharactersResponse>().toRMCharacters()
        }
    }
}
