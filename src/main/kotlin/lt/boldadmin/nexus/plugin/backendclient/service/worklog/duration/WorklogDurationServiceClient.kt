package lt.boldadmin.nexus.plugin.backendclient.service.worklog.duration

import lt.boldadmin.nexus.api.service.worklog.duration.WorklogDurationService
import lt.boldadmin.nexus.plugin.backendclient.get

class WorklogDurationServiceClient: WorklogDurationService {

    override fun measureDuration(intervalId: String) = get("/worklog/interval/$intervalId/duration", Long::class.java)

    override fun sumWorkDurations(workLogIntervalIds: Collection<String>)
        = get("/worklog/intervals/${workLogIntervalIds.joinToString(",")}/durations-sum", Long::class.java)
}