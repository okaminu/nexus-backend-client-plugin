package lt.boldadmin.nexus.plugin.backendclient.service.worklog.duration

import lt.boldadmin.nexus.api.service.worklog.duration.WorklogDurationService
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WorklogDurationServiceClient: WorklogDurationService {

    override fun measureDuration(intervalId: String): Long {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/worklog/interval/$intervalId/duration"))
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
            .uri(createUri("/worklog/intervals/${workLogIntervalIds.joinToString(",")}/durations-sum"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()
            .toLong()
    }
}