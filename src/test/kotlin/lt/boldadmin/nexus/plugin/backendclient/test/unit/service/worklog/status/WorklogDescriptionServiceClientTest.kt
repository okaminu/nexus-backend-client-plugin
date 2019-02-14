package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.WorklogDescriptionServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class WorklogDescriptionServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClient: WorklogDescriptionServiceClient

    @Before
    fun setUp() {
        serviceClient = WorklogDescriptionServiceClient(httpClientSpy)
    }

    @Test
    fun `Gets description by interval`() {
        val expectedDescription = "description"
        val intervalId = "intervalId"
        doReturn(expectedDescription)
            .`when`(httpClientSpy)
            .get("/worklog/interval/$intervalId/status/description")

        val actualDescription = serviceClient.getDescription(intervalId)

        assertSame(expectedDescription, actualDescription)
    }

    @Test
    fun `Updates description`() {
        val description = "description"
        val intervalId = "intervalId"

        serviceClient.updateDescription(intervalId, description)

        verify(httpClientSpy).post("/worklog/interval/$intervalId/status/description/update", description)
    }

    @Test
    fun `Updates description by collaborator id`() {
        val description = "description"
        val collaboratorId = "collaboratorId"

        serviceClient.updateDescriptionByCollaboratorId(collaboratorId, description)

        verify(httpClientSpy).post("/worklog/collaborator/$collaboratorId/status/description/update", description)
    }
}