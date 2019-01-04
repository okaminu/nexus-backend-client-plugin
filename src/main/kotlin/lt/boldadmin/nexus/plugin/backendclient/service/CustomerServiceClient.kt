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
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/customer/save"))
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(customer)))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun createWithDefaults(userId: String): Customer {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/customer/user/$userId/create-with-defaults"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Customer::class.java)
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
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/customer/$customerId/attribute/order-number/update"))
            .POST(HttpRequest.BodyPublishers.ofString(orderNumber.toString()))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }
}