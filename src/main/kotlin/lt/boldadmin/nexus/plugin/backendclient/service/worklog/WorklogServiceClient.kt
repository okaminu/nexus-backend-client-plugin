package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.worklog.WorklogService
import lt.boldadmin.nexus.api.type.entity.Worklog
import lt.boldadmin.nexus.plugin.backendclient.get
import lt.boldadmin.nexus.plugin.backendclient.postJson

class WorklogServiceClient: WorklogService {

    override fun save(worklog: Worklog) = postJson("/worklog/save", worklog)

    override fun getByCollaboratorId(id: String)
        = get("/worklog/collaborator/$id", object: TypeReference<Collection<Worklog>>(){})

    override fun getByProjectId(id: String)
        = get("/worklog/project/$id", object: TypeReference<Collection<Worklog>>(){})

    override fun getIntervalEndpoints(intervalId: String)
        = get("/worklog/interval/$intervalId/endpoints", object: TypeReference<Collection<Worklog>>(){})

    override fun existsByProjectIdAndCollaboratorId(projectId: String, collaboratorId: String)
        = get("/worklog/project/$projectId/collaborator/$collaboratorId/exists", Boolean::class.java)
}