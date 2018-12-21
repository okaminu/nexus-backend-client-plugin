package lt.boldadmin.nexus.plugin.backendclient.service.worklog.duration

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.worklog.duration.WorklogDurationService
import lt.boldadmin.nexus.api.type.entity.Worklog
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogDurationServiceClient: WorklogDurationService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun measureDuration(intervalId: String): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sumWorkDurations(workLogIntervalIds: Collection<String>): Long {
        if (workLogIntervalIds.isEmpty()) return 0

        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/intervals/${workLogIntervalIds.joinToString(",")}/durations-sum"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        response.body()
        return ObjectMapper().readValue(response.body(), object: TypeReference<Collection<Worklog>>(){})
    }
}