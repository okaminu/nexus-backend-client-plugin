package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.ProjectService
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class ProjectServiceClient(private val httpClient: BackendHttpClient): ProjectService {

    override fun createWithDefaults(userId: String)
        = httpClient.get("/project/user/$userId/create-with-defaults", Project::class.java)

    override fun getById(projectId: String) = httpClient.get("/project/$projectId", Project::class.java)

    override fun update(id: String, attributeName: String, attributeValue: String)
        = httpClient.post("/project/$id/attribute/$attributeName/update", attributeValue)

    override fun updateOrderNumber(projectId: String, orderNumber: Short)
        = httpClient.post("/project/$projectId/attribute/order-number/update", orderNumber)
}