package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogStartEndService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.entity.Project
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class WorklogStartEndServiceClient: WorklogStartEndService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun getProjectOfStartedWork(collaboratorId: String): Project {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start(collaborator: Collaborator, project: Project) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start(collaborator: Collaborator, project: Project, timestamp: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasWorkStarted(collaboratorId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun end(collaborator: Collaborator) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun end(collaborator: Collaborator, timestamp: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun endAllStartedWorkWhereWorkTimeEnded() {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/worklog/status/end/all-started-work-where-worktime-ended"))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun hasWorkEnded(collaboratorId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}