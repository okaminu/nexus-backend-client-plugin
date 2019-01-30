package lt.boldadmin.nexus.plugin.backendclient.httpclient

import lt.boldadmin.nexus.plugin.backendclient.httpclient.exception.BackendAddressNotSetException

class BackendAddressProvider {
    fun getProtocol() = System.getenv("NEXUS_BACKEND_PROTOCOL") ?: throw BackendAddressNotSetException()
    fun getBaseUrl() = System.getenv("NEXUS_BACKEND_BASE_URL") ?: throw BackendAddressNotSetException()
    fun getPort(): Int {
        val value = System.getenv("NEXUS_BACKEND_PORT") ?: throw BackendAddressNotSetException()
        return value.toInt()
    }
}