package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog

import com.nhaarman.mockitokotlin2.verify
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.WorklogOvertimeServiceClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class WorklogOvertimeServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClient: WorklogOvertimeServiceClient

    @BeforeEach
    fun setUp() {
        serviceClient = WorklogOvertimeServiceClient(
            httpClientSpy
        )
    }

    @Test
    fun `Ends all started work on collaborators whose work time has ended`() {
        serviceClient.endOnOvertime()

        verify(httpClientSpy)
            .post("/worklog/overtime/end")
    }

}
