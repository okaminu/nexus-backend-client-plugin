package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location

import lt.boldadmin.nexus.api.service.worklog.status.location.WorklogLocationService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class WorklogLocationServiceClient(private val httpClient: BackendHttpClient): WorklogLocationService {

    override fun logWork(collaborator: Collaborator, collaboratorCoordinates: Coordinates) =
        httpClient.postAsJson("/worklog/status/log-work/location", Pair(collaborator, collaboratorCoordinates))
}