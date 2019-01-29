package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.CustomerService
import lt.boldadmin.nexus.api.type.entity.Customer
import lt.boldadmin.nexus.plugin.backendclient.get
import lt.boldadmin.nexus.plugin.backendclient.post
import lt.boldadmin.nexus.plugin.backendclient.postJson

class CustomerServiceClient: CustomerService {

    override fun save(customer: Customer)
        = postJson("/customer/save", customer)

    override fun createWithDefaults(userId: String)
        = get("/customer/user/$userId/create-with-defaults", Customer::class.java)

    override fun getById(id: String) = get("/customer/$id", Customer::class.java)

    override fun update(id: String, attributeName: String, attributeValue: String)
        = post("/customer/$id/attribute/$attributeName/update", attributeValue)

    override fun updateOrderNumber(customerId: String, orderNumber: Short)
        = post("/customer/$customerId/attribute/order-number/update", orderNumber)
}