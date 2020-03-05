package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.fasterxml.jackson.core.type.TypeReference
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import lt.boldadmin.nexus.api.type.valueobject.WeekConstraintViolation
import lt.boldadmin.nexus.api.type.valueobject.time.DayMinuteInterval
import lt.boldadmin.nexus.api.type.valueobject.time.MinuteInterval
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.collaborator.WorkWeekValidatorClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.SATURDAY

@ExtendWith(MockKExtension::class)
class WorkWeekValidatorClientTest {

    @MockK
    private lateinit var httpClientStub: BackendHttpClient

    private lateinit var validatorClient: WorkWeekValidatorClient

    @BeforeEach
    fun setUp() {
        validatorClient = WorkWeekValidatorClient(httpClientStub)
    }

    @Test
    fun `Validates work week`() {
        val expectedViolations = setOf(WeekConstraintViolation("message", SATURDAY))
        val workWeek = sortedSetOf(DayMinuteInterval(MONDAY, MinuteInterval(10, 20), false))
        every {
            httpClientStub.postJson("/collaborator/work-week/validate",
                workWeek,
                any<TypeReference<Set<WeekConstraintViolation>>>()
            )
        } returns expectedViolations

        val actualViolations = validatorClient.validate(workWeek)

        assertEquals(expectedViolations, actualViolations)
    }
}
