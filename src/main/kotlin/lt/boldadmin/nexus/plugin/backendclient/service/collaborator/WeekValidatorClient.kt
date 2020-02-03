package lt.boldadmin.nexus.plugin.backendclient.service.collaborator

import lt.boldadmin.nexus.api.service.collaborator.WeekValidatorService
import lt.boldadmin.nexus.api.type.valueobject.Day
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import java.util.*

class WeekValidatorClient(private val httpClient: BackendHttpClient): WeekValidatorService {

    override fun validate(workWeek: SortedSet<Day>) =
        httpClient.postAsJson("/collaborator/work-week/validate", workWeek)

}
