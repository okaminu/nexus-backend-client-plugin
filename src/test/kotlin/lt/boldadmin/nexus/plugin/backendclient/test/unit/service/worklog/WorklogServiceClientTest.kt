package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Worklog
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.WorklogServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class WorklogServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var worklogServiceClientSpy: WorklogServiceClient

    @Before
    fun setUp() {
        worklogServiceClientSpy = WorklogServiceClient(httpClientSpy)
    }

    @Test
    fun `Gets worklogs by collaborator id`() {
        val expectedWorklogs = listOf(Worklog(id = "worklog1"), Worklog(id = "worklog2"))
        val collaboratorId = "collaboratorId"
        doReturn(expectedWorklogs)
            .`when`(httpClientSpy)
            .get(eq("/worklog/collaborator/$collaboratorId"), any<TypeReference<Collection<Worklog>>>())

        val actualWorklogs = worklogServiceClientSpy.getByCollaboratorId(collaboratorId)

        assertSame(expectedWorklogs, actualWorklogs)
    }

    @Test
    fun `Gets worklogs by project id`() {
        val expectedWorklogs = listOf(Worklog(id = "worklog1"), Worklog(id = "worklog2"))
        val projectId = "projectId"
        doReturn(expectedWorklogs)
            .`when`(httpClientSpy)
            .get(eq("/worklog/project/$projectId"), any<TypeReference<Collection<Worklog>>>())

        val actualWorklogs = worklogServiceClientSpy.getByProjectId(projectId)

        assertSame(expectedWorklogs, actualWorklogs)
    }

    @Test
    fun `Gets interval endpoints`() {
        val expectedWorklogs = listOf(Worklog(id = "worklog1"), Worklog(id = "worklog2"))
        val intervalId = "intervalId"
        doReturn(expectedWorklogs)
            .`when`(httpClientSpy)
            .get(eq("/worklog/interval/$intervalId/endpoints"), any<TypeReference<Collection<Worklog>>>())

        val actualWorklogs = worklogServiceClientSpy.getIntervalEndpoints(intervalId)

        assertSame(expectedWorklogs, actualWorklogs)
    }

    @Test
    fun `Exists by collaborator and project ids`() {
        val projectId = "projectId"
        val collaboratorId = "collaboratorId"
        doReturn(true).`when`(httpClientSpy).get("/worklog/project/$projectId/collaborator/$collaboratorId/exists", Boolean::class.java)

        val exists = worklogServiceClientSpy.existsByProjectIdAndCollaboratorId(projectId, collaboratorId)

        assertTrue(exists)
    }

    @Test
    fun `Saves worklog`() {
        val worklog = Worklog()

        worklogServiceClientSpy.save(worklog)

        verify(httpClientSpy).postAsJson("/worklog/save", worklog)
    }
}