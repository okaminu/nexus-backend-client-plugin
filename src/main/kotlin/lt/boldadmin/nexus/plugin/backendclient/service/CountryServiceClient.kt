package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.CountryService
import lt.boldadmin.nexus.api.type.valueobject.Country

class CountryServiceClient: CountryService {
    override val countries: Collection<Country>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}