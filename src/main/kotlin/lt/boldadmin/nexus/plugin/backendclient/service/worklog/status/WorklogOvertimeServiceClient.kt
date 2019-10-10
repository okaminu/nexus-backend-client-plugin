package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogOvertimeService
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient


class WorklogOvertimeServiceClient(private val httpClient: BackendHttpClient): WorklogOvertimeService {

    override fun endOnOvertime() =
        httpClient.postWithoutBody("/worklog/overtime/end/work-in-progress")

}
