package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogsEndService
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient


class WorklogsEndServiceClient(private val httpClient: BackendHttpClient): WorklogsEndService {

    override fun endAllStartedWorkWhereWorkTimeEnded() =
        httpClient.postWithoutBody("/worklog/status/end/all-started-work-on-ended-work-time")

}
