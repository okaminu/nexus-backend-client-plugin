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
import java.net.http.HttpResponse

class BackendHttpClient(
    private val httpClient: HttpClient,
    private val objectMapper: ObjectMapper,
    private val backendAddressProvider: BackendAddressProvider
) {

    fun <T> get(path: String, clazz: Class<T>) =
        objectMapper.readValue(get(path), clazz) ?: throw CannotConvertJsonException

    fun <T> get(path: String, typeReference: TypeReference<T>): T =
        objectMapper.readValue(get(path), typeReference) ?: throw CannotConvertJsonException

    fun get(path: String) =
        get(
            createRequestBuilder(path)
                .GET()
                .build()
        ).body() ?: throw NoBodyException

    fun <T> post(path: String, valueType: T) = post(path, objectMapper.writeValueAsString(valueType))

    fun <T> postAsJson(path: String, valueType: T) {
        post(
            createRequestBuilder(path)
                .headers("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(valueType)))
                .build()
        )
    }

    fun post(path: String, value: String) {
        post(
            createRequestBuilder(path)
                .POST(BodyPublishers.ofString(value))
                .build()
        )
    }

    fun postWithoutBody(path: String) {
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

    private fun get(request: HttpRequest) = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

    private fun post(request: HttpRequest) = httpClient.send(request, HttpResponse.BodyHandlers.discarding())

    private fun delete(request: HttpRequest) = httpClient.send(request, HttpResponse.BodyHandlers.discarding())
}
