package lt.boldadmin.nexus.plugin.backendclient.service.collaborator

import com.fasterxml.jackson.core.type.TypeReference
import lt.boldadmin.nexus.api.service.collaborator.CollaboratorCoordinatesService
import lt.boldadmin.nexus.api.type.entity.collaborator.CollaboratorCoordinates
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class CollaboratorCoordinatesServiceClient(
    private val httpClient: BackendHttpClient
): CollaboratorCoordinatesService {

    override fun getByCollaboratorId(id: String): Collection<CollaboratorCoordinates> =
        httpClient.get(
            "/collaborator/$id/coordinates",
            object : TypeReference<Collection<CollaboratorCoordinates>>() {}
        )
}
