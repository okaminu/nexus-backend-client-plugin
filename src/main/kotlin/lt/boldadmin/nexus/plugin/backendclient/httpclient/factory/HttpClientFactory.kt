package lt.boldadmin.nexus.plugin.backendclient.httpclient.factory

import java.net.http.HttpClient

object HttpClientFactory {
    fun create() = HttpClient.newBuilder().build()!!
}