package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.StartedProjectWorkTokenService
import lt.boldadmin.nexus.api.type.entity.Project

class StartedProjectWorkTokenServiceClient: StartedProjectWorkTokenService {
    override fun findTokenById(projectId: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findIdByToken(token: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findProjectByToken(token: String): Project {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findWorkingCollaboratorIdsByToken(token: String): List<String?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(projectId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}