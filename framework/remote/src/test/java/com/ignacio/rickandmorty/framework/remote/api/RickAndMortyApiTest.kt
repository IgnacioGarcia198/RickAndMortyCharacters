package com.ignacio.rickandmorty.framework.remote.api

import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.data.paging.datasource.remote.RickAndMortyApi
import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.paging.models.RMCharacters
import com.ignacio.rickandmorty.framework.remote.di.NetworkModule
import com.ignacio.rickandmorty.framework.remote.exceptions.NetworkException
import com.ignacio.rickandmorty.framework.remote.mapping.toRMCharacters
import com.ignacio.rickandmorty.framework.remote.models.RMCharactersResponse
import com.ignacio.rickandmorty.framework.remote.models.RMLinkTrait
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.JsonConvertException
import io.ktor.utils.io.ByteReadChannel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class RickAndMortyApiTest {
    private lateinit var httpClient: HttpClient
    private lateinit var api: RickAndMortyApi

    private val successResponse: RMCharactersResponse = RMCharactersResponse.Success(
        info = RMCharactersResponse.Info(
            count = 1,
            next = "",
            prev = "",
            pages = 2,
        ),
        characters = listOf(
            RMCharactersResponse.RMCharacter(
                created = "created",
                episode = emptyList(),
                gender = "female",
                id = 1,
                image = "image",
                location = RMLinkTrait(
                    name = "here",
                    url = null,
                ),
                name = "name",
                origin = RMLinkTrait(
                    name = "here",
                    url = null,
                ),
                species = "species",
                status = "alive",
                type = null,
                url = "url",
            )
        )
    )

    private val errorResponse: RMCharactersResponse =
        RMCharactersResponse.Error(error = "There is nothing here")
    private val query = CharacterQueryCriteria.default


    private fun mockEngine(string: String, status: HttpStatusCode) {
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(string),
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        httpClient = NetworkModule.provideHttpClient(mockEngine).config {
            install(ResponseObserver) {
                onResponse { response ->
                    val content = "HTTP response content: ${response.bodyAsText()}"
                    println(status)
                    println(content)
                }
            }
        }
        api = RealRickAndMortyApi(client = httpClient)
    }

    @Test
    fun `api returns success with characters if http request goes well and returns characters`() =
        runBlocking {
            val json = Json.encodeToString(
                successResponse
            )
            mockEngine(json, HttpStatusCode.OK)

            val result = api.getCharacters(1, query)

            assertTrue(result.isSuccess)
            assertEquals(successResponse.toRMCharacters(), result.getOrThrow())
        }

    @Test
    fun `api returns success with no results if http request goes well but return no characters`() =
        runBlocking {
            val json = Json.encodeToString(
                errorResponse
            )
            mockEngine(json, HttpStatusCode.NotFound)

            val result = api.getCharacters(1, query)

            assertTrue(result.isSuccess)
            assertEquals(errorResponse.toRMCharacters(), result.getOrThrow())
        }

    @Test
    fun `api returns error if http request returns a bad response`() = runBlocking {
        val json = "error"
        mockEngine(json, HttpStatusCode.OK)

        val result = api.getCharacters(1, query)

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull() as NetworkException
        assertEquals(200, exception.errorCode)
        assertEquals("OK", exception.errorDescription)
        assertTrue(exception.cause is JsonConvertException)
    }
}