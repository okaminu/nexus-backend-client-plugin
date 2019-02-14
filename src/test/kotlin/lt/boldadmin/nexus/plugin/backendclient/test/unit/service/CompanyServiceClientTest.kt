package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Company
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.CompanyServiceClient
import org.junit.Test
import kotlin.test.assertTrue

class CompanyServiceClientTest {

    @Test
    fun `Saves company`() {
        val httpClientSpy: BackendHttpClient = mock()
        val company = Company()

        CompanyServiceClient(httpClientSpy).save(company)

        verify(httpClientSpy).postAsJson("/company/save", company)
    }

    @Test
    fun `Exists by name`() {
        val httpClientDummy: BackendHttpClient = mock()
        val companyName = "companyName"
        doReturn(true).`when`(httpClientDummy).get("/company/name/$companyName/exists", Boolean::class.java)

        val exists = CompanyServiceClient(httpClientDummy).existsByName(companyName)

        assertTrue(exists)
    }

}