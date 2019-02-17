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

    private lateinit var serviceClient: WorklogStartEndServiceClient

    @Before
    fun setUp() {
        serviceClient = WorklogStartEndServiceClient(httpClientSpy)
    }

    @Test
    fun `Gets project of started work`() {
        val expectedProject = Project()
        val collaboratorId = "collaboratorId"
        doReturn(expectedProject)
            .`when`(httpClientSpy)
            .get("/worklog/collaborator/$collaboratorId/status/project-of-started-work", Project::class.java)

        val actualProject = serviceClient.getProjectOfStartedWork(collaboratorId)

        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Starts work for collaborator on project`() {
        val project = Project()
        val collaborator = Collaborator()

        serviceClient.start(collaborator, project)

        verify(httpClientSpy).postAsJson("/worklog/status/start", Pair(collaborator, project))
    }

    @Test
    fun `Starts work for collaborator on project at given timestamp`() {
        val project = Project()
        val collaborator = Collaborator()
        val timestamp: Long = 1234

        serviceClient.start(collaborator, project, timestamp)

        verify(httpClientSpy)
            .postAsJson("/worklog/status/start/timestamp/$timestamp", Pair(collaborator, project))
    }

    @Test
    fun `Ends work for collaborator on project`() {
        val collaborator = Collaborator()

        serviceClient.end(collaborator)

        verify(httpClientSpy).postAsJson("/worklog/status/end", collaborator)
    }

    @Test
    fun `Ends work for collaborator on project at given timestamp`() {
        val collaborator = Collaborator()
        val timestamp: Long = 1234

        serviceClient.end(collaborator, timestamp)

        verify(httpClientSpy)
            .postAsJson("/worklog/status/end/timestamp/$timestamp", collaborator)
    }

    @Test
    fun `Ends all started work on collaborators whose work time has ended`() {
        serviceClient.endAllStartedWorkWhereWorkTimeEnded()

        verify(httpClientSpy)
            .postWithoutBody("/worklog/status/end/all-started-work-on-ended-work-time")
    }

    @Test
    fun `Work has started for collaborator`() {
        val collaboratorId = "collaboratorId"
        doReturn(true)
            .`when`(httpClientSpy)
            .get("/worklog/collaborator/$collaboratorId/status/has-work-started", Boolean::class.java)

        val hasStarted = serviceClient.hasWorkStarted(collaboratorId)

        assertTrue(hasStarted)
    }

    @Test
    fun `Work has started for collaborator in a project`() {
        val collaboratorId = "collaboratorId"
        val projectId = "projectId"
        doReturn(true)
            .`when`(httpClientSpy)
            .get(
                "/worklog/collaborator/$collaboratorId/project/$projectId/status/has-work-started",
                Boolean::class.java
            )

        val hasStarted = serviceClient.hasWorkStarted(collaboratorId, projectId)

        assertTrue(hasStarted)
    }

    @Test
    fun `Work has ended for collaborator`() {
        val collaboratorId = "collaboratorId"
        doReturn(true)
            .`when`(httpClientSpy)
            .get("/worklog/collaborator/$collaboratorId/status/has-work-ended", Boolean::class.java)

        val hasStarted = serviceClient.hasWorkEnded(collaboratorId)

        assertTrue(hasStarted)
    }
}