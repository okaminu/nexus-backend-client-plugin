package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.CollaboratorService
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.plugin.backendclient.get
import lt.boldadmin.nexus.plugin.backendclient.post

class CollaboratorServiceClient: CollaboratorService {

    override fun save(collaborator: Collaborator) = post("/collaborator/save", collaborator)

    override fun getById(id: String) = get("/collaborator/$id", Collaborator::class.java)

    override fun getByMobileNumber(number: String)
        = get("/collaborator/mobile-number/$number", Collaborator::class.java)

    override fun createWithDefaults() = get("/collaborator/create-with-defaults", Collaborator::class.java)

    override fun existsById(id: String) = get("/collaborator/$id/exists", Boolean::class.java)

    override fun existsByMobileNumber(number: String)
        = get("/collaborator/mobile-number/$number/exists", Boolean::class.java)

    override fun update(id: String, attributeName: String, attributeValue: String)
        = post("/collaborator/$id/attribute/$attributeName/update", attributeValue)

    override fun updateOrderNumber(collaboratorId: String, orderNumber: Short)
        = post("/collaborator/$collaboratorId/attribute/order-number/update", orderNumber)
}