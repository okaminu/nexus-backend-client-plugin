package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.worklog.status.location.WorklogLocationService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.valueobject.Location
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogLocationServiceClient : WorklogLocationService {

    override fun logWork(collaborator: Collaborator, collaboratorLocation: Location) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/status/log-work/location"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(
                    ObjectMapper().writeValueAsString(Pair(collaborator, collaboratorLocation)))
            )
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }
}