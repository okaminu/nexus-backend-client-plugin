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
    fun `Gets worklog durations sum by collaborator id`() {
        val expectedDurationsSum: Long = 200
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get("/worklog/collaborator/id/durations-sum", Long::class.java)

        val actualDurationsSum = serviceClient.sumWorkDurationsByCollaboratorId("id")

        assertEquals(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `Gets worklog durations sum by project id`() {
        val expectedDurationsSum: Long = 200
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get("/worklog/project/id/durations-sum", Long::class.java)

        val actualDurationsSum = serviceClient.sumWorkDurationsByProjectId("id")

        assertEquals(expectedDurationsSum, actualDurationsSum)
    }
}