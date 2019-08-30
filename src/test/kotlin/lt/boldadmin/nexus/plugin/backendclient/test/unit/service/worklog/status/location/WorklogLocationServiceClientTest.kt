package lt.boldadmin.nexus.plugin.backendclient.test.unit.service.worklog.status.location

import io.mockk.*
import io.mockk.impl.annotations.MockK
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.backendclient.kafka.factory.KafkaProducerFactory
import lt.boldadmin.nexus.plugin.backendclient.kafka.factory.PropertiesFactory
import lt.boldadmin.nexus.plugin.backendclient.kafka.serializer.CollaboratorCoordinatesSerializer
import lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location.WorklogLocationServiceClient
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.Before
import org.junit.Test
import java.util.*

class WorklogLocationServiceClientTest {

    @MockK
    private lateinit var producerSpy: KafkaProducer<String, Pair<String, Coordinates>>

    @MockK
    private lateinit var propertiesFactory: PropertiesFactory

    @MockK
    private lateinit var factorySpy: KafkaProducerFactory

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Logs work by location`() {
        val properties = Properties()
        val collaboratorId = "collaboratorId"
        val coordinates = Coordinates(123.0, 123.0)
        every { factorySpy.create<Pair<String, Coordinates>>(properties) } returns producerSpy
        every { propertiesFactory.create(CollaboratorCoordinatesSerializer::class.java) } returns properties
        every { producerSpy.send(any()) } returns mockk()

        WorklogLocationServiceClient(factorySpy, propertiesFactory).logWork(collaboratorId, coordinates)

        val collaboratorCoordinates = Pair(collaboratorId, coordinates)
        verify { producerSpy.send(eq(ProducerRecord("collaborator-location-update", collaboratorCoordinates))) }
    }
}