package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.location

import lt.boldadmin.nexus.api.service.worklog.status.location.WorklogLocationService
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.backendclient.kafka.factory.KafkaProducerFactory
import lt.boldadmin.nexus.plugin.backendclient.kafka.factory.PropertiesFactory
import lt.boldadmin.nexus.plugin.backendclient.kafka.serializer.CollaboratorCoordinatesSerializer
import org.apache.kafka.clients.producer.ProducerRecord

class WorklogLocationServiceClient(
    private val producerFactory: KafkaProducerFactory,
    private val propertiesFactory: PropertiesFactory
): WorklogLocationService {

    override fun logWork(collaboratorId: String, coordinates: Coordinates) {
        producerFactory
            .create<Pair<String, Coordinates>>(propertiesFactory.create(CollaboratorCoordinatesSerializer::class.java))
            .send(ProducerRecord("collaborator-location-update", Pair(collaboratorId, coordinates)))
    }
}