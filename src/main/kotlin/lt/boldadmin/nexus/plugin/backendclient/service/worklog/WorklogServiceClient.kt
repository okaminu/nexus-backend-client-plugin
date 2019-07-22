package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.worklog.WorklogService
import lt.boldadmin.nexus.api.type.entity.Worklog
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class WorklogServiceClient(private val httpClient: BackendHttpClient): WorklogService {

    override fun save(worklog: Worklog) = httpClient.postAsJson("/worklog/save", worklog)

    override fun getByCollaboratorId(id: String) =
        httpClient.get("/worklog/collaborator/$id", object: TypeReference<Collection<Worklog>>() {})

    override fun getByProjectId(id: String) =
        httpClient.get("/worklog/project/$id", object: TypeReference<Collection<Worklog>>() {})

    override fun getIntervalEndpoints(intervalId: String) =
        httpClient.get("/worklog/interval/$intervalId/endpoints", object: TypeReference<Collection<Worklog>>() {})

}
