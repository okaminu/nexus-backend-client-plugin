package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status.message

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.valueobject.Message
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.message.WorklogMessageServiceClient
import org.junit.Test

class WorklogMessageServiceClientTest {

    @Test
    fun `Logs work by message`() {
        val httpClientSpy: BackendHttpClient = mock()
        val message = Message("1234", "4321", "hello")

        WorklogMessageServiceClient(httpClientSpy).logWork(message)

        verify(httpClientSpy).postJson("/worklog/status/log-work/message", message)
    }
}