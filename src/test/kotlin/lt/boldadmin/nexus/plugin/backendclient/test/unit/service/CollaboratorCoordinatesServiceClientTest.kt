package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.fasterxml.jackson.core.type.TypeReference
import io.mockk.every
import io.mockk.mockk
import lt.boldadmin.nexus.api.type.valueobject.CollaboratorCoordinates
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.collaborator.CollaboratorCoordinatesServiceClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class CollaboratorCoordinatesServiceClientTest {

    @Test
    fun `Gets collaborator's coordinates`() {
        val httpClientSpy: BackendHttpClient = mockk()
        val expectedCoordinates = listOf(CollaboratorCoordinates("collabId", Coordinates(1.2, 3.4), 123))
        every {
            httpClientSpy.get(
                "/collaborator/collabId/coordinates",
                any<TypeReference<Collection<CollaboratorCoordinates>>>()
            )
        } returns expectedCoordinates

        val actualCoordinates = CollaboratorCoordinatesServiceClient(httpClientSpy).getByCollaboratorId("collabId")

        assertSame(expectedCoordinates, actualCoordinates)
    }
}
