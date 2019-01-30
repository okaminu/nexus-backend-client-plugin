package lt.boldadmin.nexus.plugin.backendclient.httpclient.factory

import java.net.http.HttpClient

class HttpClientFactory {
    fun create() = HttpClient.newBuilder().build()!!
}