package lt.boldadmin.nexus.plugin.backendclient.test.unit.eventpublisher

import io.mockk.every
import io.mockk.mockk
import lt.boldadmin.nexus.plugin.backendclient.kafka.KafkaServerAddressProvider
import lt.boldadmin.nexus.plugin.backendclient.kafka.PropertiesFactory
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import java.math.BigInteger
import java.util.*
import kotlin.test.assertEquals

class PropertiesFactoryTest {

    @Test
    fun `Creates properties`() {
        val addressProviderSpy = mockk<KafkaServerAddressProvider>()
        every { addressProviderSpy.url } returns "url"
        val expectedProperties = Properties().apply {
            this["bootstrap.servers"] = "url"
            this["key.serializer"] = StringSerializer::class.java
            this["value.serializer"] = BigInteger::class.java
        }

        val actualProperties = PropertiesFactory(addressProviderSpy).create(BigInteger::class.java)

        assertEquals(expectedProperties, actualProperties)
    }
}