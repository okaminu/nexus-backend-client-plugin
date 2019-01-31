package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status.location

import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.api.type.valueobject.Location
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location.WorklogLocationServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WorklogLocationServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClientSpy: WorklogLocationServiceClient

    @Before
    fun setUp() {
        serviceClientSpy = WorklogLocationServiceClient(httpClientSpy)
    }

    @Test
    fun `Logs work by location`() {
        val collaborator = Collaborator()
        val location = Location(123.0, 123.0)

        serviceClientSpy.logWork(collaborator, location)

        verify(httpClientSpy).postJson("/worklog/status/log-work/location", Pair(collaborator, location))
    }
}