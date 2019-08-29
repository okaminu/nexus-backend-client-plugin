package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status.location

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location.WorklogLocationServiceClient
import org.junit.Test

class WorklogLocationServiceClientTest {

    @Test
    fun `Logs work by location`() {
        val httpClientSpy: BackendHttpClient = mock()
        val collaboratorId = "collabId"
        val coordinates = Coordinates(123.0, 123.0)

        WorklogLocationServiceClient(httpClientSpy).logWork(collaboratorId, coordinates)

        verify(httpClientSpy).postAsJson("/worklog/status/log-work/location", Pair(collaboratorId, coordinates))
    }
}