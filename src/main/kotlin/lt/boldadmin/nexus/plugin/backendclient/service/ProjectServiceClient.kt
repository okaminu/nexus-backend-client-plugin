package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.ProjectService
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.get
import lt.boldadmin.nexus.plugin.backendclient.post

class ProjectServiceClient: ProjectService {

    override fun createWithDefaults(userId: String)
        = get("/project/user/$userId/create-with-defaults", Project::class.java)

    override fun getById(projectId: String) = get("/project/$projectId", Project::class.java)

    override fun update(id: String, attributeName: String, attributeValue: String)
        = post("/project/$id/attribute/$attributeName/update", attributeValue)

    override fun updateOrderNumber(projectId: String, orderNumber: Short)
        = post("/project/$projectId/attribute/order-number/update", orderNumber)
}