package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.worklog.WorklogService
import lt.boldadmin.nexus.api.type.entity.Worklog
import lt.boldadmin.nexus.api.type.valueobject.DateRange
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class WorklogServiceClient(private val httpClient: BackendHttpClient): WorklogService {
    override fun getIntervalIdsByCollaboratorId(id: String, dateRange: DateRange): Collection<String> =
        httpClient.get(
        "/worklog/collaborator/$id/start/${dateRange.start.format()}/end/${dateRange.end.format()}/interval-ids",
        object : TypeReference<Collection<String>>() {}
    )

    override fun getIntervalIdsByProjectId(id: String, dateRange: DateRange): Collection<String> =
        httpClient.get(
            "/worklog/project/$id/start/${dateRange.start.format()}/end/${dateRange.end.format()}/interval-ids",
            object : TypeReference<Collection<String>>() {}
    )

    override fun save(worklog: Worklog) = httpClient.postAsJson("/worklog/save", worklog)

    override fun getIntervalIdsByCollaboratorId(id: String): Collection<String> =
        httpClient.get("/worklog/collaborator/$id/interval-ids", object : TypeReference<Collection<String>>() {})

    override fun getIntervalIdsByProjectId(id: String): Collection<String> =
        httpClient.get("/worklog/project/$id/interval-ids", object: TypeReference<Collection<String>>() {})

    override fun getIntervalEndpoints(intervalId: String) =
        httpClient.get("/worklog/interval/$intervalId/endpoints", object: TypeReference<Collection<Worklog>>() {})
}
