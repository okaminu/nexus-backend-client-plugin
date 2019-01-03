package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.StartedProjectWorkTokenService
import lt.boldadmin.nexus.api.type.entity.Project
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class StartedProjectWorkTokenServiceClient: StartedProjectWorkTokenService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun findTokenById(projectId: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findIdByToken(token: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findProjectByToken(token: String): Project {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findWorkingCollaboratorIdsByToken(token: String): List<String?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(projectId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/started-project-work-token/project/$projectId/exists"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Boolean::class.java)
    }
}