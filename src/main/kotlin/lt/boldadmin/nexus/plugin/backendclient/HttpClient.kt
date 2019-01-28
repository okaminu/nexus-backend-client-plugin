package lt.boldadmin.nexus.plugin.backendclient

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun <T>get(path: String, clazz: Class<T>): T {
    val request = HttpRequest.newBuilder()
        .uri(createUri(path))
        .GET()
        .build()

    val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
    return ObjectMapper().readValue(response.body(), clazz)
}

fun <T>post(path: String, value: T) = post(path, ObjectMapper().writeValueAsString(value))

fun post(path: String, value: String) {
    val request = HttpRequest.newBuilder()
        .uri(createUri(path))
        .headers("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(value))
        .build()

    HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
}