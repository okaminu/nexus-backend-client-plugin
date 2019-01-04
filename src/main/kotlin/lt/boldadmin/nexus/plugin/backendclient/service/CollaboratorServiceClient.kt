package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.CollaboratorService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CollaboratorServiceClient: CollaboratorService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun save(collaborator: Collaborator) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: String): Collaborator {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/collaborator/$id"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Collaborator::class.java)
    }

    override fun getByMobileNumber(number: String): Collaborator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createWithDefaults(): Collaborator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(id: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByMobileNumber(number: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(id: String, attributeName: String, attributeValue: String) {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/collaborator/$id/attribute/$attributeName/update"))
            .POST(HttpRequest.BodyPublishers.ofString(attributeValue))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun updateOrderNumber(collaboratorId: String, orderNumber: Short) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}