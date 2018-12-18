package lt.boldadmin.nexus.plugin.backendclient

import lt.boldadmin.nexus.api.service.worklog.status.WorklogStartEndService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.entity.Project
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class WorklogStartEndServiceClient: WorklogStartEndService {
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
            .uri(URI("http://127.0.0.1:8070/worklog/status/end/all-started-work-where-worktime-ended"))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
    }

    override fun hasWorkEnded(collaboratorId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}