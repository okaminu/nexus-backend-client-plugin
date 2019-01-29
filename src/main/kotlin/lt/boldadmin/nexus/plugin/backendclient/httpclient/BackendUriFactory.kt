package lt.boldadmin.nexus.plugin.backendclient.httpclient

import java.net.URI

class BackendUriFactory(
    private val protocol: String = "http",
    private val baseUrl: String = "127.0.0.1",
    private val port: Int = 8070
) {
    fun create(path: String) = URI(protocol, null, baseUrl, port, path, null, null)
}
