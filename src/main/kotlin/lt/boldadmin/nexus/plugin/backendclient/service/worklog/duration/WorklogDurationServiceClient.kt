package lt.boldadmin.nexus.plugin.backendclient.service.worklog.duration

import lt.boldadmin.nexus.api.service.worklog.duration.WorklogDurationService
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogDurationServiceClient: WorklogDurationService {

    private val baseUrl = "http://127.0.0.1:8070"

    override fun measureDuration(intervalId: String): Long {
        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/worklog/interval/$intervalId/duration"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()
            .toLong()
    }

    override fun sumWorkDurations(workLogIntervalIds: Collection<String>): Long {
        if (workLogIntervalIds.isEmpty()) return 0

        val request = HttpRequest.newBuilder()
            .uri(URI("$baseUrl/worklog/intervals/${workLogIntervalIds.joinToString(",")}/durations-sum"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()
            .toLong()
    }
}