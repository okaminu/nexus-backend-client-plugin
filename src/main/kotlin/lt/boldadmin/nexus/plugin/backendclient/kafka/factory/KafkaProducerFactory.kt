package lt.boldadmin.nexus.plugin.backendclient.kafka.factory

import org.apache.kafka.clients.producer.KafkaProducer
import java.util.*

object KafkaProducerFactory {
    fun <T>create(properties: Properties) = KafkaProducer<String, T>(properties)
}