package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.worklog.status.WorklogStartEndService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class WorklogStartEndServiceClient: WorklogStartEndService {

    override fun getProjectOfStartedWork(collaboratorId: String): Project {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/collaborator/$collaboratorId/status/project-of-started-work"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Project::class.java)
    }

    override fun start(collaborator: Collaborator, project: Project) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/status/start"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(Pair(collaborator, project))))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun start(collaborator: Collaborator, project: Project, timestamp: Long) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/status/start/timestamp/$timestamp"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(Pair(collaborator, project))))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun hasWorkStarted(collaboratorId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/collaborator/$collaboratorId/status/has-work-started"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }

    override fun end(collaborator: Collaborator) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/status/end"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(collaborator)))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun end(collaborator: Collaborator, timestamp: Long) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/status/end/timestamp/$timestamp"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(collaborator)))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun endAllStartedWorkWhereWorkTimeEnded() {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/status/end/all-started-work-where-worktime-ended"))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun hasWorkEnded(collaboratorId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/collaborator/$collaboratorId/status/has-work-ended"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }


}