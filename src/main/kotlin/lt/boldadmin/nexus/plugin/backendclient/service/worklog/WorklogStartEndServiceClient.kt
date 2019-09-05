package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import lt.boldadmin.nexus.api.service.worklog.WorklogStartEndService
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient


class WorklogStartEndServiceClient(private val httpClient: BackendHttpClient):
    WorklogStartEndService {

    override fun getProjectOfStartedWork(collaboratorId: String) =
        httpClient.get("/worklog/collaborator/$collaboratorId/status/project-of-started-work", Project::class.java)

    override fun hasWorkStarted(collaboratorId: String) =
        httpClient.get("/worklog/collaborator/$collaboratorId/status/has-work-started", Boolean::class.java)

    override fun hasWorkStarted(collaboratorId: String, projectId: String) =
        httpClient.get(
            "/worklog/collaborator/$collaboratorId/project/$projectId/status/has-work-started",
            Boolean::class.java
        )

    override fun endAllStartedWorkWhereWorkTimeEnded() =
        httpClient.postWithoutBody("/worklog/status/end/all-started-work-on-ended-work-time")
}