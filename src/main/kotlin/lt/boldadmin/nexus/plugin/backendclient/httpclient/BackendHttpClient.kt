package lt.boldadmin.nexus.plugin.backendclient.httpclient

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.plugin.backendclient.httpclient.exception.CannotConvertJsonException
import lt.boldadmin.nexus.plugin.backendclient.httpclient.exception.NoBodyException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpRequest.newBuilder
import java.net.http.HttpResponse.BodyHandlers

class BackendHttpClient(
    private val httpClient: HttpClient,
    private val objectMapper: ObjectMapper,
    private val backendAddressProvider: BackendAddressProvider
) {

    fun <T> get(path: String, clazz: Class<T>) =
        objectMapper.readValue(get(path), clazz) ?: throw CannotConvertJsonException

    fun <T> get(path: String, responseType: TypeReference<T>): T =
        objectMapper.readValue(get(path), responseType) ?: throw CannotConvertJsonException

    fun get(path: String) =
        get(createRequestBuilder(path).GET().build()).body() ?: throw NoBodyException

    fun <T> post(path: String, value: T) {
        post(path, objectMapper.writeValueAsString(value))
    }

    fun <I, O> postJson(path: String, value: I, responseType: TypeReference<O>): O {
        val httpResponse = postWithResponse(createPostJsonRequest(path, value))
        return objectMapper.readValue(httpResponse.body(), responseType)
    }

    fun <T> postJson(path: String, value: T) {
        post(createPostJsonRequest(path, value))
    }

    fun post(path: String, value: String) {
        post(
            createRequestBuilder(path)
                .POST(BodyPublishers.ofString(value))
                .build()
        )
    }

    fun post(path: String) {
        post(
            createRequestBuilder(path)
                .POST(BodyPublishers.noBody())
                .build()
        )
    }

    fun delete(path: String) {
        delete(
            createRequestBuilder(path)
                .DELETE()
                .build()
        )
    }

    private fun <T> createPostJsonRequest(path: String, value: T) =
        createRequestBuilder(path)
            .headers("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(value)))
            .build()

    private fun createRequestBuilder(path: String) = newBuilder().uri(createUri(path))

    private fun createUri(path: String) = URI(
        backendAddressProvider.protocol,
        null,
        backendAddressProvider.baseUrl,
        backendAddressProvider.port,
        path,
        null,
        null
    )

    private fun get(request: HttpRequest) = httpClient.send(request, BodyHandlers.ofString())

    private fun post(request: HttpRequest) { httpClient.send(request, BodyHandlers.discarding()) }

    private fun postWithResponse(request: HttpRequest) = httpClient.send(request, BodyHandlers.ofString())

    private fun delete(request: HttpRequest) { httpClient.send(request, BodyHandlers.discarding()) }
}
