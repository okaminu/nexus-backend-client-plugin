package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.worklog.WorklogService
import lt.boldadmin.nexus.api.type.entity.Worklog
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogServiceClient: WorklogService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun getByCollaboratorId(id: String): Collection<Worklog> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getByProjectId(id: String): Collection<Worklog> {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/worklog/project/$id"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        response.body()
        return ObjectMapper().readValue(response.body(), object: TypeReference<Collection<Worklog>>(){})
    }

    override fun getIntervalEndpoints(intervalId: String): Collection<Worklog> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByProjectIdAndCollaboratorId(projectId: String, collaboratorId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}