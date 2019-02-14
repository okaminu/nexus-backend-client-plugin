package lt.boldadmin.nexus.plugin.backendclient.httpclient

import lt.boldadmin.nexus.plugin.backendclient.httpclient.exception.BackendAddressNotSetException

object BackendAddressProvider {
    val protocol get() = System.getenv("NEXUS_BACKEND_PROTOCOL") ?: throw BackendAddressNotSetException

    val baseUrl get() = System.getenv("NEXUS_BACKEND_BASE_URL") ?: throw BackendAddressNotSetException

    val port: Int
        get() {
            val value = System.getenv("NEXUS_BACKEND_PORT") ?: throw BackendAddressNotSetException
            return value.toInt()
        }
}