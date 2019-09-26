package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.fasterxml.jackson.core.type.TypeReference
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import lt.boldadmin.nexus.api.type.entity.collaborator.CollaboratorCoordinates
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.collaborator.CollaboratorCoordinatesServiceClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CollaboratorCoordinatesServiceClientTest {

    @MockK
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClient: CollaboratorCoordinatesServiceClient

    @BeforeEach
    fun setUp() {
        serviceClient = CollaboratorCoordinatesServiceClient(httpClientSpy)
    }

    @Test
    fun `Gets all countries`() {
        val expectedCoordinates = listOf(CollaboratorCoordinates("collabId", Coordinates(1.2, 3.4), 123))
        every {
            httpClientSpy.get(
                "/collaborator/collabId/coordinates",
                any<TypeReference<Collection<CollaboratorCoordinates>>>()
            )
        } returns expectedCoordinates

        val actualCoordinates = serviceClient.getByCollaboratorId("collabId")

        assertSame(expectedCoordinates, actualCoordinates)
    }
}
