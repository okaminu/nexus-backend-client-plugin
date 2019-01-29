package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.CountryService
import lt.boldadmin.nexus.api.type.valueobject.Country
import lt.boldadmin.nexus.plugin.backendclient.get

class CountryServiceClient: CountryService {

    override val countries: Collection<Country>
        get() = get("/countries", object : TypeReference<Collection<Country>>() {})
}