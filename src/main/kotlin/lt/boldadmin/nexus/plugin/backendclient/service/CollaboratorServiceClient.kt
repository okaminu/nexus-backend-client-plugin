package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.CollaboratorService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CollaboratorServiceClient: CollaboratorService {

    override fun save(collaborator: Collaborator) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/collaborator/save"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(collaborator)))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun getById(id: String): Collaborator {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/collaborator/$id"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Collaborator::class.java)
    }

    override fun getByMobileNumber(number: String): Collaborator {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/collaborator/mobile-number/$number"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Collaborator::class.java)
    }

    override fun createWithDefaults(): Collaborator {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/collaborator/create-with-defaults"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Collaborator::class.java)
    }

    override fun existsById(id: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/collaborator/$id/exists"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }

    override fun existsByMobileNumber(number: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/collaborator/mobile-number/$number/exists"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }

    override fun update(id: String, attributeName: String, attributeValue: String) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/collaborator/$id/attribute/$attributeName/update"))
            .POST(HttpRequest.BodyPublishers.ofString(attributeValue))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun updateOrderNumber(collaboratorId: String, orderNumber: Short) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/collaborator/$collaboratorId/attribute/order-number/update"))
            .POST(HttpRequest.BodyPublishers.ofString(orderNumber.toString()))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }
}