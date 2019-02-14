package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status.location

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.valueobject.Location
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location.WorklogLocationServiceClient
import org.junit.Test

class WorklogLocationServiceClientTest {

    @Test
    fun `Logs work by location`() {
        val httpClientSpy: BackendHttpClient = mock()
        val collaborator = Collaborator()
        val location = Location(123.0, 123.0)

        WorklogLocationServiceClient(httpClientSpy).logWork(collaborator, location)

        verify(httpClientSpy).postAsJson("/worklog/status/log-work/location", Pair(collaborator, location))
    }
}