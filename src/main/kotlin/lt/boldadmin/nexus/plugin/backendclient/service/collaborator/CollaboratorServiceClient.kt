package lt.boldadmin.nexus.plugin.backendclient.service.collaborator

import lt.boldadmin.nexus.api.service.collaborator.CollaboratorService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.valueobject.DayMinuteInterval
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import java.util.*

class CollaboratorServiceClient(private val httpClient: BackendHttpClient): CollaboratorService {

    override fun save(collaborator: Collaborator) = httpClient.post("/collaborator/save", collaborator)

    override fun getById(id: String) = httpClient.get("/collaborator/$id", Collaborator::class.java)

    override fun getByMobileNumber(number: String) =
        httpClient.get("/collaborator/mobile-number/$number", Collaborator::class.java)

    override fun createWithDefaults() = httpClient.get("/collaborator/create-with-defaults", Collaborator::class.java)

    override fun existsById(id: String) = httpClient.get("/collaborator/$id/exists", Boolean::class.java)

    override fun existsByMobileNumber(number: String) =
        httpClient.get("/collaborator/mobile-number/$number/exists", Boolean::class.java)

    override fun update(id: String, attributeName: String, attributeValue: String) =
        httpClient.post("/collaborator/$id/attribute/$attributeName/update", attributeValue)

    override fun update(id: String, workWeek: SortedSet<DayMinuteInterval>) {
        httpClient.postJson("/collaborator/$id/work-week/update", workWeek)
    }

    override fun updateOrderNumber(collaboratorId: String, orderNumber: Short) =
        httpClient.post("/collaborator/$collaboratorId/attribute/order-number/update", orderNumber)
}
