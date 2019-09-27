package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogOvertimeService
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient


class WorklogOvertimeServiceClient(private val httpClient: BackendHttpClient): WorklogOvertimeService {

    override fun endAllStartedWorkWhereWorkTimeEnded() =
        httpClient.postWithoutBody("/worklog/status/end/all-started-work-on-ended-work-time")

}
