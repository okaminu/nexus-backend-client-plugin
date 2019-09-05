package lt.boldadmin.nexus.plugin.backendclient.event.publisher

import lt.boldadmin.nexus.api.event.publisher.CollaboratorLocationPublisher
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.api.type.valueobject.Message
import lt.boldadmin.nexus.plugin.backendclient.kafka.factory.KafkaProducerFactory
import lt.boldadmin.nexus.plugin.backendclient.kafka.factory.PropertiesFactory
import lt.boldadmin.nexus.plugin.backendclient.kafka.serializer.CollaboratorCoordinatesSerializer
import lt.boldadmin.nexus.plugin.backendclient.kafka.serializer.MessageSerializer
import org.apache.kafka.clients.producer.ProducerRecord

class CollaboratorLocationPublisherAdapter(
    private val producerFactory: KafkaProducerFactory,
    private val propertiesFactory: PropertiesFactory
): CollaboratorLocationPublisher {

    override fun publish(collaboratorId: String, coordinates: Coordinates) {
        producerFactory
            .create<Pair<String, Coordinates>>(propertiesFactory.create(CollaboratorCoordinatesSerializer::class.java))
            .send(ProducerRecord("collaborator-location-update-by-coordinates", Pair(collaboratorId, coordinates)))
    }

    override fun publish(message: Message) {
        producerFactory
            .create<Message>(propertiesFactory.create(MessageSerializer::class.java))
            .send(ProducerRecord("collaborator-location-update-by-message", message))
    }
}