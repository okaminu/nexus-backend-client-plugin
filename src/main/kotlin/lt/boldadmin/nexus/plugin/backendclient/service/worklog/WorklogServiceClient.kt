package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.worklog.WorklogService
import lt.boldadmin.nexus.api.type.entity.Worklog
import lt.boldadmin.nexus.api.type.valueobject.time.DateInterval
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import java.time.LocalDate

class WorklogServiceClient(private val httpClient: BackendHttpClient): WorklogService {
    override fun getIntervalIdsByCollaboratorId(id: String, dateInterval: DateInterval): Collection<String> =
        httpClient.get(
            formatToUrl("collaborator", id, dateInterval.start, dateInterval.end),
            object : TypeReference<Collection<String>>() {}
        )

    override fun getIntervalIdsByProjectId(id: String, dateInterval: DateInterval): Collection<String> =
        httpClient.get(
            formatToUrl("project", id, dateInterval.start, dateInterval.end),
            object : TypeReference<Collection<String>>() {}
        )

    override fun save(worklog: Worklog) {
        httpClient.postJson("/worklog/save", worklog)
    }

    override fun getIntervalIdsByCollaboratorId(id: String): Collection<String> =
        httpClient.get("/worklog/collaborator/$id/interval-ids", object : TypeReference<Collection<String>>() {})

    override fun getIntervalIdsByProjectId(id: String): Collection<String> =
        httpClient.get("/worklog/project/$id/interval-ids", object: TypeReference<Collection<String>>() {})

    override fun getIntervalEndpoints(intervalId: String) =
        httpClient.get("/worklog/interval/$intervalId/endpoints", object: TypeReference<Collection<Worklog>>() {})

    private fun formatToUrl(entityType: String, id: String, start: LocalDate, end: LocalDate) =
        "/worklog/%s/%s/start/%s/end/%s/interval-ids".format(entityType, id, start.format(), end.format())
}
