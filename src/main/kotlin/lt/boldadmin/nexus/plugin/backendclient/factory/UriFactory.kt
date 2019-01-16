package lt.boldadmin.nexus.plugin.backendclient.factory

import java.net.URI

fun createUri(path: String) = URI("http", null, "127.0.0.1", 8070, path, null, null)