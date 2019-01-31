package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.fasterxml.jackson.core.type.TypeReference
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import lt.boldadmin.nexus.api.type.valueobject.Country
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.CountryServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class CountryServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var countryServiceClientSpy: CountryServiceClient

    @Before
    fun setUp() {
        countryServiceClientSpy = CountryServiceClient(httpClientSpy)
    }

    @Test
    fun `Gets all countries`() {
        val expectedCountries = listOf(Country("Narnia"), Country("Middle-Earth"))
        doReturn(expectedCountries).`when`(httpClientSpy).get(eq("/countries"), any<TypeReference<Collection<Country>>>())

        val actualCountries = countryServiceClientSpy.countries

        assertSame(expectedCountries, actualCountries)
    }

}