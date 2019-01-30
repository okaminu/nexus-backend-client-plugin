package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.CountryService
import lt.boldadmin.nexus.api.type.valueobject.Country
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class CountryServiceClient(private val httpClient: BackendHttpClient): CountryService {

    override val countries: Collection<Country>
        get() = httpClient.get("/countries", object : TypeReference<Collection<Country>>() {})
}