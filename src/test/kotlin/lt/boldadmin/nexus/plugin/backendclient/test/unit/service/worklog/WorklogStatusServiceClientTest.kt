package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.WorklogStatusServiceClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class WorklogStatusServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClient: WorklogStatusServiceClient

    @BeforeEach
    fun setUp() {
        serviceClient = WorklogStatusServiceClient(httpClientSpy)
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
}
