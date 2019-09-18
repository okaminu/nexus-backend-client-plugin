package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogStartEndService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient


class WorklogStartEndServiceClient(private val httpClient: BackendHttpClient): WorklogStartEndService {

    override fun getProjectOfStartedWork(collaboratorId: String) =
        httpClient.get("/worklog/collaborator/$collaboratorId/status/project-of-started-work", Project::class.java)

    override fun start(collaborator: Collaborator, project: Project) =
        httpClient.postAsJson("/worklog/status/start", Pair(collaborator, project))

    override fun start(collaborator: Collaborator, project: Project, timestamp: Long) =
        httpClient.postAsJson("/worklog/status/start/timestamp/$timestamp", Pair(collaborator, project))

    override fun hasWorkStarted(collaboratorId: String) =
        httpClient.get("/worklog/collaborator/$collaboratorId/status/has-work-started", Boolean::class.java)

    override fun hasWorkStarted(collaboratorId: String, projectId: String) =
        httpClient.get(
            "/worklog/collaborator/$collaboratorId/project/$projectId/status/has-work-started",
            Boolean::class.java
        )

    override fun end(collaborator: Collaborator) =
        httpClient.postAsJson("/worklog/status/end", collaborator)

    override fun end(collaborator: Collaborator, timestamp: Long) =
        httpClient.postAsJson("/worklog/status/end/timestamp/$timestamp", collaborator)

    override fun hasWorkEnded(collaboratorId: String) =
        httpClient.get("/worklog/collaborator/$collaboratorId/status/has-work-ended", Boolean::class.java)
}
