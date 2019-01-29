package lt.boldadmin.nexus.plugin.backendclient.httpclient

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class BackendHttpClient(
    private val requestBuilder: HttpRequest.Builder = HttpRequest.newBuilder(),
    private val httpClient: HttpClient = HttpClient.newBuilder().build(),
    private val objectMapper: ObjectMapper = ObjectMapper(),
    private val backendUriFactory: BackendUriFactory = BackendUriFactory()
) {
    fun <T> get(path: String, clazz: Class<T>) = objectMapper.readValue(get(path).body(), clazz)!!

    fun <T> get(path: String, typeReference: TypeReference<T>): T = objectMapper.readValue(
        get(path).body(),
        typeReference
    )

    fun <T> post(path: String, value: T) = post(path, objectMapper.writeValueAsString(value))

    fun <T> postJson(path: String, value: T) {
        post(
            requestBuilder.uri(backendUriFactory.create(path))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(value)))
                .build()
        )
    }

    fun post(path: String, value: String) {
        post(
            requestBuilder.uri(backendUriFactory.create(path))
                .POST(HttpRequest.BodyPublishers.ofString(value))
                .build()
        )
    }

    fun postWithoutBody(path: String) {
        post(
            requestBuilder.uri(backendUriFactory.create(path))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build()
        )
    }

    private fun get(path: String) =
        get(
            requestBuilder.uri(backendUriFactory.create(path))
                .GET()
                .build()
        )

    private fun get(request: HttpRequest) = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

    private fun post(request: HttpRequest) = httpClient.send(request, HttpResponse.BodyHandlers.discarding())
}
