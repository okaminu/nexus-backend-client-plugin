package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.CompanyService
import lt.boldadmin.nexus.api.type.entity.Company
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CompanyServiceClient: CompanyService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun save(company: Company) {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/company/save"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(company)))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun existsByName(name: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/company/name/$name/exists"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Boolean::class.java)
    }

}