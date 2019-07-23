package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import com.nhaarman.mockito_kotlin.*
import lt.boldadmin.nexus.api.type.entity.Worklog
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.WorklogServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class WorklogServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var worklogServiceClient: WorklogServiceClient

    @Before
    fun setUp() {
        worklogServiceClient = WorklogServiceClient(httpClientSpy)
    }

    @Test
    fun `Gets interval ids by collaborator id`() {
        val expectedIntervalIds = listOf("intervalId1", "intervalId2")
        val collaboratorId = "collaboratorId"
        doReturn(expectedIntervalIds)
            .`when`(httpClientSpy)
            .get(eq("/worklog/collaborator/$collaboratorId/interval-ids"), any<TypeReference<Collection<String>>>())

        val actualIntervalIds = worklogServiceClient.getIntervalIdsByCollaboratorId(collaboratorId)

        assertSame(expectedIntervalIds, actualIntervalIds)
    }

    @Test
    fun `Gets interval ids by project id`() {
        val expectedIntervalIds = listOf("intervalId1", "intervalId2")
        val projectId = "projectId"
        doReturn(expectedIntervalIds)
            .`when`(httpClientSpy)
            .get(eq("/worklog/project/$projectId/interval-ids"), any<TypeReference<Collection<String>>>())

        val actualWorklogs = worklogServiceClient.getIntervalIdsByProjectId(projectId)

        assertSame(expectedIntervalIds, actualWorklogs)
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
