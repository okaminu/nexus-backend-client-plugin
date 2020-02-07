package lt.boldadmin.nexus.plugin.backendclient.httpclient.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

object ObjectMapperFactory {
    fun create(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())
}
