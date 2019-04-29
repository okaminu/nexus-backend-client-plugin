package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.duration

import com.nhaarman.mockito_kotlin.doReturn
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.duration.WorklogDurationServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class WorklogDurationServiceClientTest {

    @Mock
    private lateinit var httpClientStub: BackendHttpClient

    private lateinit var serviceClient: WorklogDurationServiceClient

    @Before
    fun setUp() {
        serviceClient = WorklogDurationServiceClient(httpClientStub)
    }

    @Test
    fun `Measures duration`() {
        val intervalId = "intervalId"
        val expectedDuration: Long = 404
        doReturn(expectedDuration).`when`(httpClientStub)
            .get("/worklog/interval/$intervalId/duration", Long::class.java)

        val actualDuration = serviceClient.measureDuration(intervalId)

        assertEquals(expectedDuration, actualDuration)
    }

    @Test
    fun `Calculates sum for multiple intervals`() {
        val intervalId1 = "intervalId1"
        val intervalId2 = "intervalId2"
        val expectedDurationSum: Long = 404
        doReturn(expectedDurationSum)
            .`when`(httpClientStub)
            .get("/worklog/intervals/$intervalId1,$intervalId2/durations-sum", Long::class.java)

        val actualDurationSum = serviceClient.sumWorkDurations(listOf(intervalId1, intervalId2))

        assertEquals(expectedDurationSum, actualDurationSum)
    }

    @Test
    fun `Gets worklog durations sum by collaborator id`() {
        val collaboratorId = "id"
        val expectedDurationsSum: Long = 200
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get("/worklog/collaborator/id/durations-sum", Long::class.java)

        val actualDurationsSum = serviceClient.sumWorkDurationsByCollaboratorId(collaboratorId)

        assertEquals(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `Gets worklog durations sum by project id`() {
        val projectId = "id"
        val expectedDurationsSum: Long = 200
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get("/worklog/project/id/durations-sum", Long::class.java)

        val actualDurationsSum = serviceClient.sumWorkDurationsByProjectId(projectId)

        assertEquals(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `Collaborator has no worklog intervals when none are given`() {
        val durationSum = serviceClient.sumWorkDurations(emptyList())

        assertEquals(0, durationSum)
    }
}