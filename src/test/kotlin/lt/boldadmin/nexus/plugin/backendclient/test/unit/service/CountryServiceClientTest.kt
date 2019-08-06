package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.fasterxml.jackson.core.type.TypeReference
import com.nhaarman.mockitokotlin2.*
import lt.boldadmin.nexus.api.type.valueobject.Country
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.CountryServiceClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class CountryServiceClientTest {

    @Test
    fun `Gets all countries`() {
        val httpClientSpy: BackendHttpClient = mock()
        val expectedCountries = listOf(Country("Narnia"), Country("Middle-Earth"))
        doReturn(expectedCountries).`when`(httpClientSpy).get(eq("/countries"), any<TypeReference<Collection<Country>>>())

        val actualCountries = CountryServiceClient(httpClientSpy).countries

        assertSame(expectedCountries, actualCountries)
    }

}