package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.ProjectService
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ProjectServiceClient: ProjectService {

    override fun createWithDefaults(userId: String): Project {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/project/user/$userId/create-with-defaults"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Project::class.java)
    }

    override fun getById(projectId: String): Project {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/project/$projectId"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Project::class.java)
    }

    override fun update(id: String, attributeName: String, attributeValue: String) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/project/$id/attribute/$attributeName/update"))
            .POST(HttpRequest.BodyPublishers.ofString(attributeValue))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun updateOrderNumber(projectId: String, orderNumber: Short) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/project/$projectId/attribute/order-number/update"))
            .POST(HttpRequest.BodyPublishers.ofString(orderNumber.toString()))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }
}