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
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClientSpy: WorklogDurationServiceClient

    @Before
    fun setUp() {
        serviceClientSpy = WorklogDurationServiceClient(httpClientSpy)
    }

    @Test
    fun `Measures duration`() {
        val intervalId = "intervalId"
        val expectedDuration: Long = 404
        doReturn(expectedDuration).`when`(httpClientSpy).get("/worklog/interval/$intervalId/duration", Long::class.java)

        val actualDuration = serviceClientSpy.measureDuration(intervalId)

        assertEquals(expectedDuration, actualDuration)
    }

    @Test
    fun `Calculates sum for multiple intervals`() {
        val intervalId1 = "intervalId1"
        val intervalId2 = "intervalId2"
        val expectedDurationSum: Long = 404
        doReturn(expectedDurationSum)
            .`when`(httpClientSpy)
            .get("/worklog/intervals/$intervalId1,$intervalId2/durations-sum", Long::class.java)

        val actualDurationSum = serviceClientSpy.sumWorkDurations(listOf(intervalId1, intervalId2))

        assertEquals(expectedDurationSum, actualDurationSum)
    }

    @Test
    fun `Collaborator has no worklog intervals when none are given`() {
        val durationSum = serviceClientSpy.sumWorkDurations(emptyList())

        assertEquals(0, durationSum)
    }
}