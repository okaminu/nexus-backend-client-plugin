package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.message

import lt.boldadmin.nexus.api.service.worklog.status.message.WorklogMessageService
import lt.boldadmin.nexus.api.type.valueobject.Message
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class WorklogMessageServiceClient(private val httpClient: BackendHttpClient): WorklogMessageService {

    override fun logWork(message: Message) = httpClient.postAsJson("/worklog/status/log-work/message", message)
}