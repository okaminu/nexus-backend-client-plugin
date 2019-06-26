package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.nhaarman.mockitokotlin2.doReturn
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
    private lateinit var httpClientStub: BackendHttpClient

    private lateinit var serviceClient: WorklogAuthServiceClient

    @Before
    fun setUp() {
        serviceClient = WorklogAuthServiceClient(httpClientStub)
    }

    @Test
    fun `User has worklog interval`() {
        val userId = "userId"
        val intervalId = "intervalId"
        doReturn(true)
            .`when`(httpClientStub)
            .get("/worklog/interval/$intervalId/user/$userId/has-interval", Boolean::class.java)

        val exists = serviceClient.doesUserHaveWorkLogInterval(userId, intervalId)

        assertTrue(exists)
    }

    @Test
    fun `Collaborator has worklog interval`() {
        val collaboratorId = "collaboratorId"
        val intervalId = "intervalId"
        doReturn(true)
            .`when`(httpClientStub)
            .get("/worklog/interval/$intervalId/collaborator/$collaboratorId/has-interval", Boolean::class.java)

        val exists = serviceClient.doesCollaboratorHaveWorkLogInterval(collaboratorId, intervalId)

        assertTrue(exists)
    }

    @Test
    fun `Collaborator has multiple worklog intervals`() {
        val collaboratorId = "collaboratorId"
        val intervalId1 = "intervalId1"
        val intervalId2 = "intervalId2"
        doReturn(true).`when`(httpClientStub).get("/worklog/intervals/$intervalId1,$intervalId2" +
            "/collaborator/$collaboratorId/has-intervals", Boolean::class.java)

        val exists = serviceClient.doesCollaboratorHaveWorkLogIntervals(
            collaboratorId,
            listOf(intervalId1, intervalId2)
        )

        assertTrue(exists)
    }

    @Test
    fun `Collaborator has no worklog intervals when none are given`() {
        val exists = serviceClient.doesCollaboratorHaveWorkLogIntervals("collaboratorId", emptyList())

        assertFalse(exists)
    }
}