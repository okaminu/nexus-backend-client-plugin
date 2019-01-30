package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.UserService
import lt.boldadmin.nexus.api.type.entity.User
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class UserServiceClient(private val httpClient: BackendHttpClient): UserService {

    override fun existsAny() = httpClient.get("/user/exists-any", Boolean::class.java)

    override fun save(user: User) = httpClient.postJson("/user/save", user)

    override fun createWithDefaults() = httpClient.get("/user/create-with-defaults", User::class.java)

    override fun getById(id: String) = httpClient.get("/user/$id", User::class.java)

    override fun getByEmail(email: String) = httpClient.get("/user/email/$email", User::class.java)

    override fun existsByEmail(email: String) = httpClient.get("/user/email/$email/exists", Boolean::class.java)

    override fun getByProjectId(projectId: String) = httpClient.get("/user/project/$projectId", User::class.java)

    override fun doesUserHaveCustomer(userId: String, customerId: String)
        = httpClient.get("/user/$userId/customer/$customerId/has-customer", Boolean::class.java)

    override fun doesUserHaveProject(userId: String, projectId: String)
        = httpClient.get("/user/$userId/project/$projectId/has-project", Boolean::class.java)

    override fun doesUserHaveCollaborator(userId: String, collaboratorId: String)
        = httpClient.get("/user/$userId/collaborator/$collaboratorId/has-collaborator", Boolean::class.java)

    override fun isProjectNameUnique(projectName: String, projectId: String, userId: String)
        = httpClient.get("/user/$userId/project/$projectId/name/$projectName/is-unique", Boolean::class.java)
}