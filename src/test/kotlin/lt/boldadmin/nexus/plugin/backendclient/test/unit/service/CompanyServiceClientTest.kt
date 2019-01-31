package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Company
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.CompanyServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class CompanyServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var companyServiceClientSpy: CompanyServiceClient

    @Before
    fun setUp() {
        companyServiceClientSpy = CompanyServiceClient(httpClientSpy)
    }


    @Test
    fun `Saves company`() {
        val company = Company()

        companyServiceClientSpy.save(company)

        verify(httpClientSpy).postJson("/company/save", company)
    }

    @Test
    fun `Exists by name`() {
        val companyName = "companyName"
        doReturn(true).`when`(httpClientSpy).get("/company/name/$companyName/exists", Boolean::class.java)

        val exists = companyServiceClientSpy.existsByName(companyName)

        assertTrue(exists)
    }

}