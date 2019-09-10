package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import lt.boldadmin.nexus.api.service.worklog.duration.WorklogDurationService
import lt.boldadmin.nexus.api.type.valueobject.DateRange
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.format

class WorklogDurationServiceClient(private val httpClient: BackendHttpClient):
    WorklogDurationService {

    override fun sumWorkDurationsByCollaboratorId(collaboratorId: String, dateRange: DateRange): Long =
        httpClient.get(
            "/worklog/collaborator/$collaboratorId/" +
                "start/${dateRange.start.format()}/" +
                "end/${dateRange.end.format()}/durations-sum",
            Long::class.java)

    override fun sumWorkDurationsByProjectId(projectId: String, dateRange: DateRange): Long =
        httpClient.get(
            "/worklog/project/$projectId/" +
                "start/${dateRange.start.format()}/" +
                "end/${dateRange.end.format()}/durations-sum",
            Long::class.java)

    override fun measureDuration(intervalId: String) =
        httpClient.get("/worklog/interval/$intervalId/duration", Long::class.java)

    override fun sumWorkDurationsByCollaboratorId(collaboratorId: String) =
        httpClient.get("/worklog/collaborator/$collaboratorId/durations-sum", Long::class.java)

    override fun sumWorkDurationsByProjectId(projectId: String) =
        httpClient.get("/worklog/project/$projectId/durations-sum", Long::class.java)
}
