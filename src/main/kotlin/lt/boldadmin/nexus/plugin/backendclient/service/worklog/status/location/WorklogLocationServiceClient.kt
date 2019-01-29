package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location

import lt.boldadmin.nexus.api.service.worklog.status.location.WorklogLocationService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.valueobject.Location
import lt.boldadmin.nexus.plugin.backendclient.postJson

class WorklogLocationServiceClient : WorklogLocationService {

    override fun logWork(collaborator: Collaborator, collaboratorLocation: Location)
        = postJson("/worklog/status/log-work/location", Pair(collaborator, collaboratorLocation))
}