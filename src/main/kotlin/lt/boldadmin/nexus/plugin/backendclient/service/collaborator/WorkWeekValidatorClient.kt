package lt.boldadmin.nexus.plugin.backendclient.service.collaborator

import lt.boldadmin.nexus.api.service.collaborator.WorkWeekValidatorService
import lt.boldadmin.nexus.api.type.valueobject.Day
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import java.util.*

class WorkWeekValidatorClient(private val httpClient: BackendHttpClient): WorkWeekValidatorService {

    override fun validate(workWeek: SortedSet<Day>) =
        httpClient.postAsJson("/collaborator/work-week/validate", workWeek)

}
