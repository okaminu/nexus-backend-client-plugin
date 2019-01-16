package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogDescriptionService
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogDescriptionServiceClient: WorklogDescriptionService {

    override fun getDescription(intervalId: String): String {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/interval/$intervalId/status/description"))
            .GET()
            .build()

        return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString()).body()
    }

    override fun updateDescription(intervalId: String, description: String) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/$intervalId/status/description/update"))
            .POST(HttpRequest.BodyPublishers.ofString(description))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun updateDescriptionByCollaboratorId(collaboratorId: String, description: String) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/collaborator/$collaboratorId/status/description/update"))
            .POST(HttpRequest.BodyPublishers.ofString(description))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }
}