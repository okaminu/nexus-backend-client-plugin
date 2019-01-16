package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.CountryService
import lt.boldadmin.nexus.api.type.valueobject.Country
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CountryServiceClient: CountryService {

    override val countries: Collection<Country>
        get() {
            val request = HttpRequest.newBuilder()
                .uri(createUri("/countries"))
                .GET()
                .build()

            val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
            return ObjectMapper().readValue(response.body(), object : TypeReference<Collection<Country>>() {})
        }
}