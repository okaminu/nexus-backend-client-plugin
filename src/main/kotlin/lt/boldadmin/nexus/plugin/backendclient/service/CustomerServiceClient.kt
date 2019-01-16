package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.CustomerService
import lt.boldadmin.nexus.api.type.entity.Customer
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CustomerServiceClient: CustomerService {

    override fun save(customer: Customer) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/customer/save"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(customer)))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun createWithDefaults(userId: String): Customer {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/customer/user/$userId/create-with-defaults"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Customer::class.java)
    }

    override fun getById(id: String): Customer {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/customer/$id"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Customer::class.java)
    }

    override fun update(id: String, attributeName: String, attributeValue: String) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/customer/$id/attribute/$attributeName/update"))
            .POST(HttpRequest.BodyPublishers.ofString(attributeValue))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun updateOrderNumber(customerId: String, orderNumber: Short) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/customer/$customerId/attribute/order-number/update"))
            .POST(HttpRequest.BodyPublishers.ofString(orderNumber.toString()))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }
}