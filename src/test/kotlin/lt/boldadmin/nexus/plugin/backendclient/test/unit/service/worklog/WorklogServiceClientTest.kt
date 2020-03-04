package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import com.nhaarman.mockitokotlin2.*
import lt.boldadmin.nexus.api.type.entity.Worklog
import lt.boldadmin.nexus.api.type.valueobject.time.DateInterval
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.WorklogServiceClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

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
    fun `Gets interval ids by project id and date interval`() {
        val expectedIntervalIds = listOf("intervalId1", "intervalId2")
        val projectId = "projectId"
        val dateInterval = DateInterval(LocalDate.of(2019, 5, 17), LocalDate.of(2019, 5, 20))
        doReturn(expectedIntervalIds)
            .`when`(httpClientSpy)
            .get(eq("/worklog/project/$projectId/start/2019-05-17/end/2019-05-20/interval-ids"),
                any<TypeReference<Collection<String>>>())

        val actualWorklogs = worklogServiceClient.getIntervalIdsByProjectId(projectId, dateInterval)

        assertSame(expectedIntervalIds, actualWorklogs)
    }

    @Test
    fun `Gets interval ids by collaborator id and date interval`() {
        val expectedIntervalIds = listOf("intervalId1", "intervalId2")
        val collaboratorId = "collaboratorId"
        val dateInterval = DateInterval(LocalDate.of(2019, 5, 17), LocalDate.of(2019, 5, 20))
        doReturn(expectedIntervalIds)
            .`when`(httpClientSpy)
            .get(eq("/worklog/collaborator/$collaboratorId/start/2019-05-17/end/2019-05-20/interval-ids"),
                any<TypeReference<Collection<String>>>())

        val actualWorklogs = worklogServiceClient.getIntervalIdsByCollaboratorId(collaboratorId, dateInterval)

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

        verify(httpClientSpy).postJson("/worklog/save", worklog)
    }
}
