package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.UserService
import lt.boldadmin.nexus.api.type.entity.User
import lt.boldadmin.nexus.plugin.backendclient.get
import lt.boldadmin.nexus.plugin.backendclient.postJson

class UserServiceClient: UserService {

    override fun existsAny() = get("/user/exists-any", Boolean::class.java)

    override fun save(user: User) = postJson("/user/save", user)

    override fun createWithDefaults() = get("/user/create-with-defaults", User::class.java)

    override fun getById(id: String) = get("/user/$id", User::class.java)

    override fun getByEmail(email: String) = get("/user/email/$email", User::class.java)

    override fun existsByEmail(email: String) = get("/user/email/$email/exists", Boolean::class.java)

    override fun getByProjectId(projectId: String) = get("/user/project/$projectId", User::class.java)

    override fun doesUserHaveCustomer(userId: String, customerId: String)
        = get("/user/$userId/customer/$customerId/has-customer", Boolean::class.java)

    override fun doesUserHaveProject(userId: String, projectId: String)
        = get("/user/$userId/project/$projectId/has-project", Boolean::class.java)

    override fun doesUserHaveCollaborator(userId: String, collaboratorId: String)
        = get("/user/$userId/collaborator/$collaboratorId/has-collaborator", Boolean::class.java)

    override fun isProjectNameUnique(projectName: String, projectId: String, userId: String)
        = get("/user/$userId/project/$projectId/name/$projectName/is-unique", Boolean::class.java)
}