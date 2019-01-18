package lt.boldadmin.nexus.plugin.backendclient.service

import com.fasterxml.jackson.databind.ObjectMapper
import lt.boldadmin.nexus.api.service.UserService
import lt.boldadmin.nexus.api.type.entity.User
import lt.boldadmin.nexus.plugin.backendclient.factory.createUri
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class UserServiceClient: UserService {

    override fun existsAny(): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/exists-any"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Boolean::class.java)
    }

    override fun save(user: User) {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/save"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(ObjectMapper().writeValueAsString(user)))
            .build()

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding())
    }

    override fun createWithDefaults(): User {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/create-with-defaults"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), User::class.java)
    }

    override fun getById(id: String): User {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/$id"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), User::class.java)
    }

    override fun getByEmail(email: String): User {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/email/$email"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), User::class.java)
    }

    override fun existsByEmail(email: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/email/$email/exists"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), Boolean::class.java)
    }

    override fun getByProjectId(projectId: String): User {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/project/$projectId"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())
        return ObjectMapper().readValue(response.body(), User::class.java)
    }

    override fun doesUserHaveCustomer(userId: String, customerId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/$userId/customer/$customerId/has-customer"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }

    override fun doesUserHaveProject(userId: String, projectId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/$userId/project/$projectId/has-project"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }

    override fun doesUserHaveCollaborator(userId: String, collaboratorId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/$userId/collaborator/$collaboratorId/has-collaborator"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()

    }

    override fun isProjectNameUnique(projectName: String, projectId: String, userId: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(createUri("/user/$userId/project/$projectId/name/$projectName/is-unique"))
            .GET()
            .build()

        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()!!
            .toBoolean()
    }
}