package lt.boldadmin.nexus.plugin.backendclient.kafka.factory

import lt.boldadmin.nexus.plugin.backendclient.kafka.KafkaServerAddressProvider
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class PropertiesFactory(private val addressProvider: KafkaServerAddressProvider) {
    fun <T>create(valueSerializerClass: Class<T>) = Properties().apply {
        this["bootstrap.servers"] = addressProvider.url
        this["key.serializer"] = StringSerializer::class.java
        this["value.serializer"] = valueSerializerClass
    }
}