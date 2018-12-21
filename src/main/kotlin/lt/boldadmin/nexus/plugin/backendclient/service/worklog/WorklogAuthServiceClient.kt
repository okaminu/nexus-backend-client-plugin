package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import lt.boldadmin.nexus.api.service.worklog.WorklogAuthService

class WorklogAuthServiceClient: WorklogAuthService {
    override fun doesUserHaveWorkLogInterval(userId: String, intervalId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doesCollaboratorHaveWorkLogInterval(collaboratorId: String, intervalId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doesCollaboratorHaveWorkLogIntervals(
        collaboratorId: String,
        intervalIds: Collection<String>
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}