package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.duration

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.same
import lt.boldadmin.nexus.api.type.valueobject.DateRange
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.duration.WorklogDurationServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertSame

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
    fun `With collaborator id`() {
        val collaboratorId = "id"
        val expectedDurationsSum: Long = 200
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get("/worklog/collaborator/$collaboratorId/durations-sum", Long::class.java)

        val actualDurationsSum = serviceClient.sumWorkDurationsByCollaboratorId(collaboratorId)

        assertEquals(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `With project id`() {
        val projectId = "id"
        val expectedDurationsSum: Long = 200
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get("/worklog/project/$projectId/durations-sum", Long::class.java)

        val actualDurationsSum = serviceClient.sumWorkDurationsByProjectId(projectId)

        assertEquals(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `With project id and date range`() {
        val expectedDurationsSum = 123L
        val projectId = "projectId"
        val dateRange = DateRange(LocalDate.of(2019, 5, 17), LocalDate.of(2019, 5, 20))
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get(
                eq("/worklog/project/$projectId/start/2019-05-17/end/2019-05-20/durations-sum"),
                same(Long::class.java)
            )

        val actualDurationsSum = serviceClient.sumWorkDurationsByProjectId(projectId, dateRange)

        assertSame(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `With collaborator id and date range`() {
        val expectedDurationsSum = 123L
        val collaboratorId = "collaboratorId"
        val dateRange = DateRange(LocalDate.of(2019, 5, 17), LocalDate.of(2019, 5, 20))
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get(
                eq("/worklog/collaborator/$collaboratorId/start/2019-05-17/end/2019-05-20/durations-sum"),
                same(Long::class.java)
            )

        val actualDurationsSum = serviceClient.sumWorkDurationsByCollaboratorId(collaboratorId, dateRange)

        assertSame(expectedDurationsSum, actualDurationsSum)
    }
}
