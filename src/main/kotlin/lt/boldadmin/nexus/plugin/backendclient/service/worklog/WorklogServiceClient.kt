package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import lt.boldadmin.nexus.api.service.worklog.WorklogService
import lt.boldadmin.nexus.api.type.entity.Worklog

class WorklogServiceClient: WorklogService {
    override fun getByCollaboratorId(id: String): Collection<Worklog> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getByProjectId(id: String): Collection<Worklog> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getIntervalEndpoints(intervalId: String): Collection<Worklog> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByProjectIdAndCollaboratorId(projectId: String, collaboratorId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}