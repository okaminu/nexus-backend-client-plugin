package lt.boldadmin.nexus.plugin.backendclient.test.unit.httpclient.factory

import lt.boldadmin.nexus.plugin.backendclient.httpclient.factory.HttpClientFactory
import org.junit.Test
import kotlin.test.assertNotNull

class HttpClientFactoryTest {

    @Test
    fun `Builds http client`() {
        val httpClient = HttpClientFactory.create()
        assertNotNull(httpClient)
    }
}