package lt.boldadmin.nexus.plugin.backendclient.test.unit.httpclient

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendAddressProvider
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.httpclient.exception.CannotConvertJsonException
import lt.boldadmin.nexus.plugin.backendclient.httpclient.exception.NoBodyException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpRequest.newBuilder
import java.net.http.HttpResponse
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class BackendHttpClientTest {

    @Rule
    @JvmField
    val expectedException = ExpectedException.none()!!

    @Mock
    private lateinit var httpClientSpy: HttpClient

    @Mock
    private lateinit var objectMapperStub: ObjectMapper

    @Mock
    private lateinit var backendAddressProviderStub: BackendAddressProvider

    @Mock
    private lateinit var httpResponseStub: HttpResponse<String>

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

        doReturn(expectedResponse).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri(PATH)).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())


        val actualResponse = backendHttpClient.get(PATH)


        assertSame(expectedResponse, actualResponse)
    }

    @Test
    fun `Gets no body exception`() {
        doReturn(null).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri(PATH)).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        expectedException.expect(NoBodyException::class.java)

        backendHttpClient.get(PATH)
    }

    @Test
    fun `Gets response as instance of class`() {
        val projectAsJson = "projectAsJson"
        val expectedProject = Project()

        doReturn(expectedProject).`when`(objectMapperStub).readValue(projectAsJson, Project::class.java)
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri(PATH)).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())


        val actualProject = backendHttpClient.get(PATH, Project::class.java)


        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Gets cannot convert json exception`() {
        val projectAsJson = "projectAsJson"
        doReturn(null).`when`(objectMapperStub).readValue(projectAsJson, Project::class.java)
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri(PATH)).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())

        expectedException.expect(CannotConvertJsonException::class.java)

        backendHttpClient.get(PATH, Project::class.java)
    }

    @Test
    fun `Gets response as instance of class from type reference`() {
        val projectAsJson = "projectAsJson"
        val expectedProject = Project()

        doReturn(expectedProject)
            .`when`(objectMapperStub)
            .readValue<Project>(eq(projectAsJson), any<TypeReference<Project>>())
        doReturn(projectAsJson).`when`(httpResponseStub).body()
        val request = newBuilder().uri(createUri(PATH)).GET().build()
        doReturn(httpResponseStub).`when`(httpClientSpy).send(request, HttpResponse.BodyHandlers.ofString())


        val actualProject = backendHttpClient.get(PATH, object: TypeReference<Project>(){})


        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Posts without body`() {
        backendHttpClient.postWithoutBody(PATH)

        val request = newBuilder().uri(createUri(PATH))
            .POST(BodyPublishers.noBody())
            .build()
        verify(httpClientSpy).send(request, HttpResponse.BodyHandlers.discarding())
    }

    @Test
    fun `Posts value as string`() {
        backendHttpClient.post(PATH, "value")

        val request = newBuilder().uri(createUri(PATH))
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

        val request = newBuilder().uri(createUri(PATH))
            .POST(BodyPublishers.ofString(projectAsJson))
            .build()
        verify(httpClientSpy).send(request, HttpResponse.BodyHandlers.discarding())
    }

    @Test
    fun `Posts value as class instance with json header`() {
        val project = Project()
        val projectAsJson = "projectAsJson"
        doReturn(projectAsJson).`when`(objectMapperStub).writeValueAsString(project)

        backendHttpClient.postJson(PATH, project)

        val request = newBuilder().uri(createUri(PATH))
            .headers("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(projectAsJson))
            .build()
        verify(httpClientSpy).send(request, HttpResponse.BodyHandlers.discarding())
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
        private val PATH = "/anyPath"
    }
}