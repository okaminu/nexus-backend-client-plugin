package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogDescriptionService
import lt.boldadmin.nexus.plugin.backendclient.get
import lt.boldadmin.nexus.plugin.backendclient.post

class WorklogDescriptionServiceClient: WorklogDescriptionService {

    override fun getDescription(intervalId: String)
        = get("/worklog/interval/$intervalId/status/description", String::class.java)

    override fun updateDescription(intervalId: String, description: String)
        = post("/worklog/interval/$intervalId/status/description/update", description)

    override fun updateDescriptionByCollaboratorId(collaboratorId: String, description: String)
        = post("/worklog/collaborator/$collaboratorId/status/description/update", description)
}