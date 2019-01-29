package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import lt.boldadmin.nexus.api.service.worklog.WorklogAuthService
import lt.boldadmin.nexus.plugin.backendclient.get

class WorklogAuthServiceClient: WorklogAuthService {

    override fun doesUserHaveWorkLogInterval(userId: String, intervalId: String)
        = get("/worklog/interval/$intervalId/user/$userId/has-interval", Boolean::class.java)

    override fun doesCollaboratorHaveWorkLogInterval(collaboratorId: String, intervalId: String)
        = get("/worklog/interval/$intervalId/collaborator/$collaboratorId/has-interval", Boolean::class.java)

    override fun doesCollaboratorHaveWorkLogIntervals(collaboratorId: String, intervalIds: Collection<String>)
        = get("/worklog/intervals/${intervalIds.joinToString(",")}" +
        "/collaborator/$collaboratorId/has-intervals", Boolean::class.java)
}