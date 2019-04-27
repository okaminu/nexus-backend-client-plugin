package lt.boldadmin.nexus.plugin.backendclient.service.worklog.duration

import lt.boldadmin.nexus.api.service.worklog.duration.WorklogDurationService
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

 class WorklogDurationServiceClient(private val httpClient: BackendHttpClient): WorklogDurationService {

    override fun measureDuration(intervalId: String) = httpClient.get("/worklog/interval/$intervalId/duration", Long::class.java)

    override fun sumWorkDurations(workLogIntervalIds: Collection<String>): Long {
        if (workLogIntervalIds.isEmpty()) return 0

        return httpClient.get("/worklog/intervals/${workLogIntervalIds.joinToString(",")}/durations-sum", Long::class.java)
    }

     override fun sumWorkDurationsByCollaboratorId(collaboratorId: String): Long {
          return httpClient.get("/worklog/collaborator/$collaboratorId/durations-sum", Long::class.java)
     }
}