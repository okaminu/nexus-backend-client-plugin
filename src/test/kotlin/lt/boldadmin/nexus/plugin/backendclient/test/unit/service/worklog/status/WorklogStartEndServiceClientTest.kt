package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.WorklogStartEndServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class WorklogStartEndServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClientSpy: WorklogStartEndServiceClient

    @Before
    fun setUp() {
        serviceClientSpy = WorklogStartEndServiceClient(httpClientSpy)
    }

    @Test
    fun `Gets project of started work`() {
        val expectedProject = Project()
        val collaboratorId = "collaboratorId"
        doReturn(expectedProject)
            .`when`(httpClientSpy)
            .get("/worklog/collaborator/$collaboratorId/status/project-of-started-work", Project::class.java)

        val actualProject = serviceClientSpy.getProjectOfStartedWork(collaboratorId)

        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Starts work for collaborator on project`() {
        val project = Project()
        val collaborator = Collaborator()

        serviceClientSpy.start(collaborator, project)
        verify(httpClientSpy).postJson("/worklog/status/start", Pair(collaborator, project))
    }

    @Test
    fun `Starts work for collaborator on project at given timestamp`() {
        val project = Project()
        val collaborator = Collaborator()
        val timestamp: Long = 1234

        serviceClientSpy.start(collaborator, project, timestamp)
        verify(httpClientSpy)
            .postJson("/worklog/status/start/timestamp/$timestamp", Pair(collaborator, project))
    }

    @Test
    fun `Ends work for collaborator on project`() {
        val collaborator = Collaborator()

        serviceClientSpy.end(collaborator)

        verify(httpClientSpy).postJson("/worklog/status/end", collaborator)
    }

    @Test
    fun `Ends work for collaborator on project at given timestamp`() {
        val collaborator = Collaborator()
        val timestamp: Long = 1234

        serviceClientSpy.end(collaborator, timestamp)

        verify(httpClientSpy)
            .postJson("/worklog/status/end/timestamp/$timestamp", collaborator)
    }

    @Test
    fun `Ends all started work on collaborators whose work time has ended`() {
        serviceClientSpy.endAllStartedWorkWhereWorkTimeEnded()

        verify(httpClientSpy)
            .postWithoutBody("/worklog/status/end/all-started-work-where-worktime-ended")
    }

    @Test
    fun `Work has started for collaborator`() {
        val collaboratorId = "collaboratorId"
        doReturn(true)
            .`when`(httpClientSpy)
            .get("/worklog/collaborator/$collaboratorId/status/has-work-started", Boolean::class.java)

        val hasStarted = serviceClientSpy.hasWorkStarted(collaboratorId)

        assertTrue(hasStarted)
    }

    @Test
    fun `Work has ended for collaborator`() {
        val collaboratorId = "collaboratorId"
        doReturn(true)
            .`when`(httpClientSpy)
            .get("/worklog/collaborator/$collaboratorId/status/has-work-ended", Boolean::class.java)

        val hasStarted = serviceClientSpy.hasWorkEnded(collaboratorId)

        assertTrue(hasStarted)
    }
}