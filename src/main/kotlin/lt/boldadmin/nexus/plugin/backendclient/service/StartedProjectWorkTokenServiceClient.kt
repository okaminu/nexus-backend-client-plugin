package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.StartedProjectWorkTokenService
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class StartedProjectWorkTokenServiceClient: StartedProjectWorkTokenService {

    override fun generateAndStore(projectId: String) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/started-project-work-token/generate-and-store"))
            .POST(HttpRequest.BodyPublishers.ofString(projectId))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun deleteById(projectId: String) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/started-project-work-token/delete"))
            .POST(HttpRequest.BodyPublishers.ofString(projectId))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())     }

    override fun findTokenById(projectId: String): String {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/started-project-work-token/project/$projectId/token"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
    }

    override fun findIdByToken(token: String): String {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/started-project-work-token/token/$token/id"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
    }

    override fun findProjectByToken(token: String): Project {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/started-project-work-token/token/$token/project"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Project::class.java)
    }

    override fun findWorkingCollaboratorIdsByToken(token: String): List<String?> {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/started-project-work-token/token/$token/collaborators/working"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), object: TypeReference<List<String?>>(){})
    }

    override fun existsById(projectId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/started-project-work-token/project/$projectId/exists"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }
}