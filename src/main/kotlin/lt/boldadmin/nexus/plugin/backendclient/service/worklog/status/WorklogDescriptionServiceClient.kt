package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status

import lt.boldadmin.nexus.api.service.worklog.status.WorklogDescriptionService
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class WorklogDescriptionServiceClient(private val httpClient: BackendHttpClient): WorklogDescriptionService {

    override fun getDescription(intervalId: String)
        = httpClient.get("/worklog/interval/$intervalId/status/description", String::class.java)

    override fun updateDescription(intervalId: String, description: String)
        = httpClient.post("/worklog/interval/$intervalId/status/description/update", description)

    override fun updateDescriptionByCollaboratorId(collaboratorId: String, description: String)
        = httpClient.post("/worklog/collaborator/$collaboratorId/status/description/update", description)
}