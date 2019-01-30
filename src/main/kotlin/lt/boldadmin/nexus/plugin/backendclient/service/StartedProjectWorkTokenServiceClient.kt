package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.StartedProjectWorkTokenService
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class StartedProjectWorkTokenServiceClient(private val httpClient: BackendHttpClient):
    StartedProjectWorkTokenService {

    override fun generateAndStore(projectId: String)
        = httpClient.post("/started-project-work-token/generate-and-store", projectId)

    override fun deleteById(projectId: String)
        = httpClient.post("/started-project-work-token/delete", projectId)

    override fun findTokenById(projectId: String)
        = httpClient.get("/started-project-work-token/project/$projectId/token")

    override fun findIdByToken(token: String)
        = httpClient.get("/started-project-work-token/token/$token/id")

    override fun findProjectByToken(token: String)
        = httpClient.get("/started-project-work-token/token/$token/project", Project::class.java)

    override fun findWorkingCollaboratorIdsByToken(token: String)
        = httpClient.get("/started-project-work-token/token/$token/collaborators/working", object: TypeReference<List<String?>>(){})

    override fun existsById(projectId: String)
        = httpClient.get("/started-project-work-token/project/$projectId/exists", Boolean::class.java)
}