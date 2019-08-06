package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import com.nhaarman.mockitokotlin2.*
import lt.boldadmin.nexus.api.type.entity.Worklog
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.WorklogServiceClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class WorklogServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var worklogServiceClient: WorklogServiceClient

    @BeforeEach
    fun setUp() {
        worklogServiceClient = WorklogServiceClient(httpClientSpy)
    }

    @Test
    fun `Gets worklogs by collaborator id`() {
        val expectedWorklogs = listOf(Worklog(id = "worklog1"), Worklog(id = "worklog2"))
        val collaboratorId = "collaboratorId"
        doReturn(expectedWorklogs)
            .`when`(httpClientSpy)
            .get(eq("/worklog/collaborator/$collaboratorId"), any<TypeReference<Collection<Worklog>>>())

        val actualWorklogs = worklogServiceClient.getByCollaboratorId(collaboratorId)

        assertSame(expectedWorklogs, actualWorklogs)
    }

    @Test
    fun `Gets worklogs by project id`() {
        val expectedWorklogs = listOf(Worklog(id = "worklog1"), Worklog(id = "worklog2"))
        val projectId = "projectId"
        doReturn(expectedWorklogs)
            .`when`(httpClientSpy)
            .get(eq("/worklog/project/$projectId"), any<TypeReference<Collection<Worklog>>>())

        val actualWorklogs = worklogServiceClient.getByProjectId(projectId)

        assertSame(expectedWorklogs, actualWorklogs)
    }

    @Test
    fun `Gets interval endpoints`() {
        val expectedWorklogs = listOf(Worklog(id = "worklog1"), Worklog(id = "worklog2"))
        val intervalId = "intervalId"
        doReturn(expectedWorklogs)
            .`when`(httpClientSpy)
            .get(eq("/worklog/interval/$intervalId/endpoints"), any<TypeReference<Collection<Worklog>>>())

        val actualWorklogs = worklogServiceClient.getIntervalEndpoints(intervalId)

        assertSame(expectedWorklogs, actualWorklogs)
    }

    @Test
    fun `Saves worklog`() {
        val worklog = Worklog()

        worklogServiceClient.save(worklog)

        verify(httpClientSpy).postAsJson("/worklog/save", worklog)
    }
}