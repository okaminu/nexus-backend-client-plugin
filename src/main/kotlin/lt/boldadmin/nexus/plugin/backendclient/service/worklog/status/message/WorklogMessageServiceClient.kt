package lt.boldadmin.nexus.plugin.backendclient.service.worklog.status.message

import lt.boldadmin.nexus.api.service.worklog.status.message.WorklogMessageService
import lt.boldadmin.nexus.api.type.valueobject.Message
import lt.boldadmin.nexus.plugin.backendclient.postJson

class WorklogMessageServiceClient: WorklogMessageService {

    override fun logWork(message: Message) = postJson("/worklog/status/log-work/message", message)
}