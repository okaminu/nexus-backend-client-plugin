package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.CustomerService
import lt.boldadmin.nexus.api.type.entity.Customer
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CustomerServiceClient: CustomerService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun save(customer: Customer) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createWithDefaults(userId: String): Customer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: String): Customer {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/customer/$id"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Customer::class.java)
    }

    override fun update(id: String, attributeName: String, attributeValue: String) {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/customer/$id/attribute/$attributeName/update"))
            .POST(HttpRequest.BodyPublishers.ofString(attributeValue))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun updateOrderNumber(customerId: String, orderNumber: Short) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}