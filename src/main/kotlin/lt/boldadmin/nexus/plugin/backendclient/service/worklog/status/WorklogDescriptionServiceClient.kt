package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogDescriptionService
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogDescriptionServiceClient: WorklogDescriptionService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun getDescription(intervalId: String): String {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/worklog/interval/$intervalId/status/description"))
            .GET()
            .build()

        return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString()).body()
    }

    override fun updateDescription(intervalId: String, description: String) {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/$intervalId/status/description/update"))
            .POST(HttpRequest.BodyPublishers.ofString(description))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun updateDescriptionByCollaboratorId(collaboratorId: String, description: String) {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/worklog/collaborator/$collaboratorId/status/description/update"))
            .POST(HttpRequest.BodyPublishers.ofString(description))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }
}