package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status.message

import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.valueobject.Message
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.message.WorklogMessageServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WorklogMessageServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClientSpy: WorklogMessageServiceClient

    @Before
    fun setUp() {
        serviceClientSpy = WorklogMessageServiceClient(httpClientSpy)
    }

    @Test
    fun `Logs work by message`() {
        val message = Message("1234", "4321", "hello")

        serviceClientSpy.logWork(message)

        verify(httpClientSpy).postJson("/worklog/status/log-work/message", message)
    }
}