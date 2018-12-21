package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.UserService
import lt.boldadmin.nexus.api.type.entity.User

class UserServiceClient: UserService {
    override fun save(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createWithDefaults(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: String): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getByEmail(email: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getByProjectId(projectId: String): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doesUserHaveCustomer(userId: String, customerId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doesUserHaveProject(userId: String, projectId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doesUserHaveCollaborator(userId: String, collaboratorId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isProjectNameUnique(projectName: String, projectId: String, userId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}