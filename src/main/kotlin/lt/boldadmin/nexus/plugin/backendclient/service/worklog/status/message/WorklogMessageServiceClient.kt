package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.message

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.worklog.status.message.WorklogMessageService
import lt.boldadmin.nexus.api.type.valueobject.Message
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogMessageServiceClient: WorklogMessageService {

    override fun logWork(message: Message) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("worklog/status/log-work/message"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(message)))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }
}