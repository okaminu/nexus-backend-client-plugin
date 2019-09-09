package lt.boldadmin.nexus.plugin.backendclient.service.worklog

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.format(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE)
