package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.nhaarman.mockitokotlin2.doReturn
import lt.boldadmin.nexus.api.type.valueobject.time.DateRange
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.WorklogDurationServiceClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class WorklogDurationServiceClientTest {

    @Mock
    private lateinit var httpClientStub: BackendHttpClient

    private lateinit var serviceClient: WorklogDurationServiceClient

    @BeforeEach
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
    fun `Calculates sum of durations by collaborator`() {
        val collaboratorId = "id"
        val expectedDurationsSum: Long = 200
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get("/worklog/collaborator/$collaboratorId/durations-sum", Long::class.java)

        val actualDurationsSum = serviceClient.sumWorkDurationsByCollaboratorId(collaboratorId)

        assertEquals(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `Calculates sum of durations by project`() {
        val projectId = "id"
        val expectedDurationsSum: Long = 200
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get("/worklog/project/$projectId/durations-sum", Long::class.java)

        val actualDurationsSum = serviceClient.sumWorkDurationsByProjectId(projectId)

        assertEquals(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `Calculates sum of durations by project and date range filter`() {
        val expectedDurationsSum = 123L
        val projectId = "projectId"
        val dateRange = DateRange(LocalDate.of(2019, 5, 17), LocalDate.of(2019, 5, 20))
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get(
                "/worklog/project/$projectId/start/2019-05-17/end/2019-05-20/durations-sum",
                Long::class.java
            )

        val actualDurationsSum = serviceClient.sumWorkDurationsByProjectId(projectId, dateRange)

        assertSame(expectedDurationsSum, actualDurationsSum)
    }

    @Test
    fun `Calculates sum of durations by collaborator and date range filter`() {
        val expectedDurationsSum = 123L
        val collaboratorId = "collaboratorId"
        val dateRange = DateRange(LocalDate.of(2019, 5, 17), LocalDate.of(2019, 5, 20))
        doReturn(expectedDurationsSum)
            .`when`(httpClientStub)
            .get(
                "/worklog/collaborator/$collaboratorId/start/2019-05-17/end/2019-05-20/durations-sum",
                Long::class.java
            )

        val actualDurationsSum = serviceClient.sumWorkDurationsByCollaboratorId(collaboratorId, dateRange)

        assertSame(expectedDurationsSum, actualDurationsSum)
    }
}
