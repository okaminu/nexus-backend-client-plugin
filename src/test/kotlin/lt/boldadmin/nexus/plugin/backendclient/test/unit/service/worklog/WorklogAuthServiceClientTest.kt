package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.nhaarman.mockito_kotlin.doReturn
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.WorklogAuthServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class WorklogAuthServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClientSpy: WorklogAuthServiceClient

    @Before
    fun setUp() {
        serviceClientSpy = WorklogAuthServiceClient(httpClientSpy)
    }

    @Test
    fun `User has worklog interval`() {
        val userId = "userId"
        val intervalId = "intervalId"
        doReturn(true).`when`(httpClientSpy).get("/worklog/interval/$intervalId/user/$userId/has-interval", Boolean::class.java)

        val exists = serviceClientSpy.doesUserHaveWorkLogInterval(userId, intervalId)

        assertTrue(exists)
    }

    @Test
    fun `Collaborator has worklog interval`() {
        val collaboratorId = "collaboratorId"
        val intervalId = "intervalId"
        doReturn(true).`when`(httpClientSpy).get("/worklog/interval/$intervalId/collaborator/$collaboratorId/has-interval", Boolean::class.java)

        val exists = serviceClientSpy.doesCollaboratorHaveWorkLogInterval(collaboratorId, intervalId)

        assertTrue(exists)
    }

    @Test
    fun `Collaborator has multiple worklog intervals`() {
        val collaboratorId = "collaboratorId"
        val intervalId1 = "intervalId1"
        val intervalId2 = "intervalId2"
        doReturn(true).`when`(httpClientSpy).get("/worklog/intervals/$intervalId1,$intervalId2" +
            "/collaborator/$collaboratorId/has-intervals", Boolean::class.java)

        val exists = serviceClientSpy.doesCollaboratorHaveWorkLogIntervals(collaboratorId, listOf(intervalId1, intervalId2))

        assertTrue(exists)
    }

    @Test
    fun `Collaborator has no worklog intervals when none are given`() {
        val exists = serviceClientSpy.doesCollaboratorHaveWorkLogIntervals("collaboratorId", emptyList())

        assertFalse(exists)
    }
}