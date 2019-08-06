package lt.boldadmin.nexus.plugin.backendclient.test.unit.httpclient

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.*
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendAddressProvider
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.httpclient.exception.CannotConvertJsonException
import lt.boldadmin.nexus.plugin.backendclient.httpclient.exception.NoBodyException
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpRequest.newBuilder
import java.net.http.HttpResponse

@ExtendWith(MockitoExtension::class)
class BackendHttpClientTest {

    @Mock
    private lateinit var httpClientSpy: HttpClient

    @Mock
    private lateinit var objectMapperStub: ObjectMapper

    @Mock
    private lateinit var backendAddressProviderStub: BackendAddressProvider

    @Mock
    private lateinit var httpResponseStub: HttpResponse<String>

    private lateinit var backendHttpClient: BackendHttpClient

    @BeforeEach
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
        val request = newBuilder().uri(createUri()).GET().build()
        doReturn(expectedResponse).`when`(httpResponseStub).body()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        val actualResponse = backendHttpClient.get(PATH)

        assertSame(expectedResponse, actualResponse)
    }

    @Test
    fun `Gets no body exception`() {
        doReturn(null).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri()).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        assertThrows(NoBodyException::class.java) {
            backendHttpClient.get(PATH)
        }
    }

    @Test
    fun `Gets response as instance of class`() {
        val projectAsJson = "projectAsJson"
        val expectedProject = Project()

        doReturn(expectedProject).`when`(objectMapperStub).readValue(projectAsJson, Project::class.java)
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri()).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        val actualProject = backendHttpClient.get(PATH, Project::class.java)

        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Gets cannot convert json exception`() {
        val projectAsJson = "projectAsJson"
        doReturn(null).`when`(objectMapperStub).readValue(projectAsJson, Project::class.java)
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri()).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        assertThrows(CannotConvertJsonException::class.java) {
            backendHttpClient.get(PATH, Project::class.java)
        }
    }

    @Test
    fun `Gets cannot convert json exception on Type reference`() {
        val projectAsJson = "projectAsJson"
        doReturn(null)
            .`when`(objectMapperStub)
            .readValue<Project>(eq(projectAsJson), any<TypeReference<Project>>())
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri()).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        assertThrows(CannotConvertJsonException::class.java) {
            backendHttpClient.get(PATH, object : TypeReference<Project>() {})
        }
    }


    @Test
    fun `Gets response as instance of class from type reference`() {
        val projectAsJson = "projectAsJson"
        val expectedProject = Project()
        doReturn(expectedProject)
            .`when`(objectMapperStub)
            .readValue<Project>(eq(projectAsJson), any<TypeReference<Project>>())
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri()).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        val actualProject = backendHttpClient.get(PATH, object: TypeReference<Project>(){})

        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Posts without body`() {
        backendHttpClient.postWithoutBody(PATH)

        val request = newBuilder().uri(createUri())
            .POST(BodyPublishers.noBody())
            .build()
        verify(httpClientSpy).send(request, HttpResponse.BodyHandlers.discarding())
    }

    @Test
    fun `Posts value as string`() {
        backendHttpClient.post(PATH, "value")

        val request = newBuilder().uri(createUri())
            .POST(BodyPublishers.ofString("value"))
            .build()
        verify(httpClientSpy).send(request, HttpResponse.BodyHandlers.discarding())
    }

    @Test
    fun `Posts value as class instance`() {
        val project = Project()
        val projectAsJson = "projectAsJson"
        doReturn(projectAsJson).`when`(objectMapperStub).writeValueAsString(project)

        backendHttpClient.post(PATH, project)

        val request = newBuilder().uri(createUri())
            .POST(BodyPublishers.ofString(projectAsJson))
            .build()
        verify(httpClientSpy).send(request, HttpResponse.BodyHandlers.discarding())
    }

    @Test
    fun `Posts value as class instance with json header`() {
        val project = Project()
        val projectAsJson = "projectAsJson"
        doReturn(projectAsJson).`when`(objectMapperStub).writeValueAsString(project)

        backendHttpClient.postAsJson(PATH, project)

        val request = newBuilder().uri(createUri())
            .headers("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(projectAsJson))
            .build()
        verify(httpClientSpy).send(request, HttpResponse.BodyHandlers.discarding())
    }

    @Test
    fun `Sends DELETE request`() {
        backendHttpClient.delete(PATH)

        val request = newBuilder().uri(createUri())
            .DELETE()
            .build()
        verify(httpClientSpy).send(request, HttpResponse.BodyHandlers.discarding())
    }

    private fun createUri() = URI(
        BACKEND_PROTOCOL,
        null,
        BACKEND_BASE_URL,
        BACKEND_PORT,
        PATH,
        null,
        null
    )

    companion object {
        private val BACKEND_PROTOCOL = "http"
        private val BACKEND_BASE_URL = "hellokitty.com"
        private val BACKEND_PORT = 1234
        private val PATH = "/anyPath"
    }
}