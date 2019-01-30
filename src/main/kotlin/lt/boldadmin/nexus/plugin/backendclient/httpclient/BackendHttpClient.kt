package lt.boldadmin.nexus.plugin.backendclient.httpclient

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
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
    fun <T> get(path: String, clazz: Class<T>) = objectMapper.readValue(get(path), clazz)!!

    fun <T> get(path: String, typeReference: TypeReference<T>): T = objectMapper.readValue(get(path), typeReference)

    fun get(path: String) =
        get(
            createRequestBuilder(path)
                .GET()
                .build()
        ).body()!!

    fun <T> post(path: String, value: T) = post(path, objectMapper.writeValueAsString(value))

    fun <T> postJson(path: String, value: T) {
        post(
            createRequestBuilder(path)
                .headers("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(value)))
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

    private fun createRequestBuilder(path: String) = newBuilder().uri(createUri(path))

    private fun createUri(path: String) = URI(
        backendAddressProvider.getProtocol(),
        null,
        backendAddressProvider.getBaseUrl(),
        backendAddressProvider.getPort(),
        path,
        null,
        null
    )

    private fun get(request: HttpRequest) = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

    private fun post(request: HttpRequest) = httpClient.send(request, HttpResponse.BodyHandlers.discarding())
}
