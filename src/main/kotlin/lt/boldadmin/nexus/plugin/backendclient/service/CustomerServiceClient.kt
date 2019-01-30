package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.CustomerService
import lt.boldadmin.nexus.api.type.entity.Customer
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class CustomerServiceClient(private val httpClient: BackendHttpClient): CustomerService {

    override fun save(customer: Customer)
        = httpClient.postJson("/customer/save", customer)

    override fun createWithDefaults(userId: String)
        = httpClient.get("/customer/user/$userId/create-with-defaults", Customer::class.java)

    override fun getById(id: String) = httpClient.get("/customer/$id", Customer::class.java)

    override fun update(id: String, attributeName: String, attributeValue: String)
        = httpClient.post("/customer/$id/attribute/$attributeName/update", attributeValue)

    override fun updateOrderNumber(customerId: String, orderNumber: Short)
        = httpClient.post("/customer/$customerId/attribute/order-number/update", orderNumber)
}