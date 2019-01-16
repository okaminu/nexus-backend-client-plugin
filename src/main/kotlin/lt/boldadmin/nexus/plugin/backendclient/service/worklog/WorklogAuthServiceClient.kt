package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import lt.boldadmin.nexus.api.service.worklog.WorklogAuthService
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogAuthServiceClient: WorklogAuthService {

    override fun doesUserHaveWorkLogInterval(userId: String, intervalId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/interval/$intervalId/user/$userId/has-interval"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }

    override fun doesCollaboratorHaveWorkLogInterval(collaboratorId: String, intervalId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/interval/$intervalId/collaborator/$collaboratorId/has-interval"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }

    override fun doesCollaboratorHaveWorkLogIntervals(
        collaboratorId: String,
        intervalIds: Collection<String>
    ): Boolean {
        if (intervalIds.isEmpty()) return false

        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/intervals/${intervalIds.joinToString(",")}" +
                    "/collaborator/$collaboratorId/has-intervals"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }
}