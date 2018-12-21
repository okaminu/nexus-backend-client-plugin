package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.CustomerService
import lt.boldadmin.nexus.api.type.entity.Customer

class CustomerServiceClient: CustomerService {
    override fun save(customer: Customer) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createWithDefaults(userId: String): Customer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: String): Customer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(id: String, attributeName: String, attributeValue: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateOrderNumber(customerId: String, orderNumber: Short) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}