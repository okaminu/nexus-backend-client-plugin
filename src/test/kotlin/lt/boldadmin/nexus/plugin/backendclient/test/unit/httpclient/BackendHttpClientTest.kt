package lt.boldadmin.nexus.plugin.backendclient.test.unit.httpclient

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendAddressProvider
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class BackendHttpClientTest {

    @Mock
    private lateinit var httpClientSpy: HttpClient

    @Mock
    private lateinit var objectMapperStub: ObjectMapper

    @Mock
    private lateinit var backendAddressProviderStub: BackendAddressProvider

    private lateinit var backendHttpClient: BackendHttpClient

    @Before
    fun setUp() {
        backendHttpClient = BackendHttpClient(
            httpClientSpy,
            objectMapperStub,
            backendAddressProviderStub
        )
        doReturn(BACKEND_BASE_URL).`when`(backendAddressProviderStub).baseUrl
        doReturn(BACKEND_PORT).`when`(backendAddressProviderStub).port
        doReturn(BACKEND_PROTOCOL).`when`(backendAddressProviderStub).protocol
    }

    @Test
    fun `Gets response as string`() {
        val expectedResponse = "expectedResponse"
        val httpResponseStub: HttpResponse<String> = mock()
        val path = "/anyPath"

        doReturn(expectedResponse).`when`(httpResponseStub).body()
        val request = HttpRequest.newBuilder().uri(createUri(path))
            .GET()
            .build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        val actualResponse = backendHttpClient.get(path)

        assertEquals(expectedResponse, actualResponse)

    }

    @Test
    fun `Gets response as instance of class`() {
        val projectAsJson = "projectAsJson"
        val httpResponseStub: HttpResponse<String> = mock()
        val expectedProject = Project()
        val path = "/anyPath"
        doReturn(expectedProject).`when`(objectMapperStub).readValue(projectAsJson, Project::class.java)
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = HttpRequest.newBuilder().uri(createUri(path))
            .GET()
            .build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        val actualProject = backendHttpClient.get(path, Project::class.java)

        assertEquals(expectedProject, actualProject)

    }

    @Test
    fun `Gets response as instance of class from type reference`() {
        val projectAsJson = "projectAsJson"
        val httpResponseStub: HttpResponse<String> = mock()
        val expectedProject = Project()
        val path = "/anyPath"
        doReturn(expectedProject).`when`(objectMapperStub).readValue<Project>(eq(projectAsJson),
            any<TypeReference<Project>>())
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = HttpRequest.newBuilder().uri(createUri(path))
            .GET()
            .build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        val actualProject = backendHttpClient.get(path, object: TypeReference<Project>(){})

        assertEquals(expectedProject, actualProject)

    }

    private fun createUri(path: String) = URI(
        BACKEND_PROTOCOL,
        null,
        BACKEND_BASE_URL,
        BACKEND_PORT,
        path,
        null,
        null
    )

    companion object {
        private val BACKEND_PROTOCOL = "http"
        private val BACKEND_BASE_URL = "hellokitty.com"
        private val BACKEND_PORT = 1234
    }
}