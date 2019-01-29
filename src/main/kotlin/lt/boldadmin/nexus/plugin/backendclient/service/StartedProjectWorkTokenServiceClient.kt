package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.StartedProjectWorkTokenService
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.get
import lt.boldadmin.nexus.plugin.backendclient.post

class StartedProjectWorkTokenServiceClient: StartedProjectWorkTokenService {

    override fun generateAndStore(projectId: String)
        = post("/started-project-work-token/generate-and-store", projectId)

    override fun deleteById(projectId: String)
        = post("/started-project-work-token/delete", projectId)

    override fun findTokenById(projectId: String)
        = get("/started-project-work-token/project/$projectId/token", String::class.java)

    override fun findIdByToken(token: String)
        = get("/started-project-work-token/token/$token/id", String::class.java)

    override fun findProjectByToken(token: String)
        = get("/started-project-work-token/token/$token/project", Project::class.java)

    override fun findWorkingCollaboratorIdsByToken(token: String)
        = get("/started-project-work-token/token/$token/collaborators/working", object: TypeReference<List<String?>>(){})

    override fun existsById(projectId: String)
        = get("/started-project-work-token/project/$projectId/exists", Boolean::class.java)
}