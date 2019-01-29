package lt.boldadmin.nexus.plugin.backendclient

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun <T>get(path: String, clazz: Class<T>) = ObjectMapper().readValue(get(path).body(), clazz)!!

fun <T>get(path: String, typeReference: TypeReference<T>): T = ObjectMapper().readValue(get(path).body(), typeReference)

fun <T>post(path: String, value: T) = post(path, ObjectMapper().writeValueAsString(value))

fun <T>postJson(path: String, value: T)  {
    val request = HttpRequest.newBuilder()
        .uri(createUri(path))
        .headers("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(value)))
        .build()

    post(request)
}

fun post(path: String, value: String) {
    val request = HttpRequest.newBuilder()
        .uri(createUri(path))
        .POST(HttpRequest.BodyPublishers.ofString(value))
        .build()

    post(request)
}

fun postWithoutBody(path: String) {
    val request = HttpRequest.newBuilder()
        .uri(createUri(path))
        .POST(HttpRequest.BodyPublishers.noBody())
        .build()

    post(request)
}

private fun get(path: String): HttpResponse<String> {
    val request = HttpRequest.newBuilder()
        .uri(createUri(path))
        .GET()
        .build()

    return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
}


private fun post(request: HttpRequest) = HttpClient.newBuilder()
    .build()
    .send(request, HttpResponse.BodyHandlers.discarding())
