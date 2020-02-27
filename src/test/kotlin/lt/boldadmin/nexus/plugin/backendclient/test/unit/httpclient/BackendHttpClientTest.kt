package lt.boldadmin.nexus.plugin.backendclient.test.unit.httpclient

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import lt.boldadmin.nexus.api.type.entity.Collaborator
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
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpRequest.newBuilder
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers.discarding
import java.net.http.HttpResponse.BodyHandlers.ofString

@ExtendWith(MockKExtension::class)
class BackendHttpClientTest {

    @MockK
    private lateinit var httpClientSpy: HttpClient

    @MockK
    private lateinit var objectMapperStub: ObjectMapper

    @MockK
    private lateinit var backendAddressProviderStub: BackendAddressProvider

    @MockK
    private lateinit var httpResponseStub: HttpResponse<String>

    private lateinit var backendHttpClient: BackendHttpClient

    @BeforeEach
    fun setUp() {
        backendHttpClient = BackendHttpClient(
            httpClientSpy,
            objectMapperStub,
            backendAddressProviderStub
        )
        every { backendAddressProviderStub.baseUrl } returns BACKEND_BASE_URL
        every { backendAddressProviderStub.port } returns BACKEND_PORT
        every { backendAddressProviderStub.protocol } returns BACKEND_PROTOCOL
    }

    @Test
    fun `Gets response as string`() {
        val expectedResponse = "expectedResponse"
        val request = newBuilder().uri(createUri()).GET().build()
        every { httpResponseStub.body() } returns expectedResponse
        every { httpClientSpy.send(request, ofString()) } returns httpResponseStub

        val actualResponse = backendHttpClient.get(PATH)

        assertSame(expectedResponse, actualResponse)
    }

    @Test
    fun `Gets no body exception`() {
        every { httpResponseStub.body() } returns null
        val request = newBuilder().uri(createUri()).GET().build()
        every { httpClientSpy.send(request, ofString()) } returns httpResponseStub

        assertThrows(NoBodyException::class.java) {
            backendHttpClient.get(PATH)
        }
    }

    @Test
    fun `Gets response as instance of class`() {
        val projectAsJson = "projectAsJson"
        val expectedProject = Project()

        every { objectMapperStub.readValue(projectAsJson, Project::class.java) } returns expectedProject
        every { httpResponseStub.body() } returns projectAsJson
        val request = newBuilder().uri(createUri()).GET().build()
        every { httpClientSpy.send(request, ofString()) } returns httpResponseStub

        val actualProject = backendHttpClient.get(PATH, Project::class.java)

        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Gets cannot convert json exception`() {
        val projectAsJson = "projectAsJson"
        every { objectMapperStub.readValue(projectAsJson, Project::class.java) } returns null
        every { httpResponseStub.body() } returns projectAsJson
        val request = newBuilder().uri(createUri()).GET().build()
        every { httpClientSpy.send(request, ofString()) } returns httpResponseStub

        assertThrows(CannotConvertJsonException::class.java) {
            backendHttpClient.get(PATH, Project::class.java)
        }
    }

    @Test
    fun `Gets cannot convert json exception on Type reference`() {
        stubResponse(null)
        val request = newBuilder().uri(createUri()).GET().build()
        every { httpClientSpy.send(request, ofString()) } returns httpResponseStub

        assertThrows(CannotConvertJsonException::class.java) {
            backendHttpClient.get(PATH, object: TypeReference<Project>() {})
        }
    }

    @Test
    fun `Gets response as instance of class from type reference`() {
        val expectedProject = Project()
        stubResponse(expectedProject)
        val request = newBuilder().uri(createUri()).GET().build()
        every { httpClientSpy.send(request, ofString()) } returns httpResponseStub

        val actualProject = backendHttpClient.get(PATH, object: TypeReference<Project>() {})

        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Posts without body`() {
        val request = newBuilder().uri(createUri())
            .POST(BodyPublishers.noBody())
            .build()
        every { httpClientSpy.send(request, discarding()) } returns mockk()

        backendHttpClient.post(PATH)

        verify { httpClientSpy.send(request, discarding()) }
    }

    @Test
    fun `Posts value as string`() {
        val request = newBuilder().uri(createUri())
            .POST(BodyPublishers.ofString("value"))
            .build()
        every { httpClientSpy.send<Void>(request, discarding()) } returns mockk()

        backendHttpClient.post(PATH, "value")

        verify { httpClientSpy.send(request, discarding()) }
    }

    @Test
    fun `Posts value as class instance`() {
        val project = Project()
        val projectAsJson = "projectAsJson"
        val request = newBuilder().uri(createUri())
            .POST(BodyPublishers.ofString(projectAsJson))
            .build()
        every { objectMapperStub.writeValueAsString(project) } returns projectAsJson
        every { httpClientSpy.send(request, discarding()) } returns mockk()

        backendHttpClient.post(PATH, project)

        verify { httpClientSpy.send(request, discarding()) }
    }

    @Test
    fun `Posts value as class instance with json header`() {
        val project = Project()
        val request = stubRequest(project)
        every { httpClientSpy.send(request, discarding()) } returns mockk()

        backendHttpClient.postJson(PATH, project)

        verify { httpClientSpy.send(request, discarding()) }
    }

    @Test
    fun `Retrieves response after sending request with Post`() {
        val project = Project()
        val expectedCollaborator = Collaborator()
        stubResponse(expectedCollaborator)
        val request = stubRequest(project)
        every { httpClientSpy.send(request, ofString()) } returns httpResponseStub

        val actualCollaborator = backendHttpClient.postJson(PATH, project, object: TypeReference<Collaborator>() {})

        assertSame(expectedCollaborator, actualCollaborator)
    }

    @Test
    fun `Sends DELETE request`() {
        every { httpClientSpy.send<Void>(newBuilder(createUri()).DELETE().build(), discarding()) } returns mockk()

        backendHttpClient.delete(PATH)

        val request = newBuilder().uri(createUri())
            .DELETE()
            .build()
        verify { httpClientSpy.send(request, discarding()) }
    }

    private fun <T> stubRequest(value: T): HttpRequest {
        every { objectMapperStub.writeValueAsString(value) } returns "someJson"
        return newBuilder().uri(createUri())
            .headers("Content-Type", "application/json")
            .POST(BodyPublishers.ofString("someJson"))
            .build()
    }

    private fun <T> stubResponse(value: T?) {
        every { objectMapperStub.readValue<T>(eq("someJson"), any<TypeReference<T>>()) } returns value
        every { httpResponseStub.body() } returns "someJson"
    }

    private fun createUri() = URI(BACKEND_PROTOCOL, null, BACKEND_BASE_URL, BACKEND_PORT, PATH, null, null)

    companion object {
        private val BACKEND_PROTOCOL = "http"
        private val BACKEND_BASE_URL = "hellokitty.com"
        private val BACKEND_PORT = 1234
        private val PATH = "/anyPath"
    }
}
