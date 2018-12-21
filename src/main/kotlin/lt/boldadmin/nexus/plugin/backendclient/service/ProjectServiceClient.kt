package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.ProjectService
import lt.boldadmin.nexus.api.type.entity.Project
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ProjectServiceClient: ProjectService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun createWithDefaults(userId: String): Project {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(projectId: String): Project {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/project/$projectId"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        response.body()
        return ObjectMapper().readValue(response.body(), Project::class.java)
    }

    override fun update(id: String, attributeName: String, attributeValue: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateOrderNumber(projectId: String, orderNumber: Short) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}