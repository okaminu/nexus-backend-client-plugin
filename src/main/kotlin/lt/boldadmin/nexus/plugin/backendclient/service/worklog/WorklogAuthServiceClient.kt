package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import lt.boldadmin.nexus.api.service.worklog.WorklogAuthService
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogAuthServiceClient: WorklogAuthService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun doesUserHaveWorkLogInterval(userId: String, intervalId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/worklog/interval/$intervalId/user/$userId/has-interval"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }

    override fun doesCollaboratorHaveWorkLogInterval(collaboratorId: String, intervalId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doesCollaboratorHaveWorkLogIntervals(
        collaboratorId: String,
        intervalIds: Collection<String>
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}