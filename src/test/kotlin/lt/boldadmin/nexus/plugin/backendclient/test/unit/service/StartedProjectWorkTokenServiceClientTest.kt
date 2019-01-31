package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.fasterxml.jackson.core.type.TypeReference
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.StartedProjectWorkTokenServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class StartedProjectWorkTokenServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClientSpy: StartedProjectWorkTokenServiceClient

    @Before
    fun setUp() {
        serviceClientSpy = StartedProjectWorkTokenServiceClient(httpClientSpy)
    }

    @Test
    fun `Generates and stores token for project`() {
        val projectId = "projectId"

        serviceClientSpy.generateAndStore(projectId)

        verify(httpClientSpy).post("/started-project-work-token/generate-and-store", projectId)
    }

    @Test
    fun `Deletes token`() {
        val projectId = "projectId"

        serviceClientSpy.deleteById(projectId)

        verify(httpClientSpy).post("/started-project-work-token/delete", projectId)
    }

    @Test
    fun `Finds token by id`() {
        val projectId = "projectId"
        val expectedToken = "token"
        doReturn(expectedToken).`when`(httpClientSpy).get("/started-project-work-token/project/$projectId/token")

        val actualToken = serviceClientSpy.findTokenById(projectId)

        assertEquals(expectedToken, actualToken)
    }

    @Test
    fun `Finds id by token`() {
        val expectedProjectId = "projectId"
        val token = "token"
        doReturn(expectedProjectId).`when`(httpClientSpy).get("/started-project-work-token/token/$token/id")

        val actualProjectId = serviceClientSpy.findIdByToken(token)

        assertEquals(expectedProjectId, actualProjectId)
    }

    @Test
    fun `Finds project by token`() {
        val expectedProject = Project()
        val token = "token"
        doReturn(expectedProject)
            .`when`(httpClientSpy)
            .get("/started-project-work-token/token/$token/project", Project::class.java)

        val actualProject = serviceClientSpy.findProjectByToken(token)

        assertEquals(expectedProject, actualProject)
    }

    @Test
    fun `Finds working collaborator ids by token`() {
        val expectedIds = listOf("collabId1", "collabId2")
        val token = "token"
        doReturn(expectedIds)
            .`when`(httpClientSpy)
            .get(eq("/started-project-work-token/token/$token/collaborators/working"),
                any<TypeReference<List<String?>>>())

        val actualIds = serviceClientSpy.findWorkingCollaboratorIdsByToken(token)

        assertEquals(expectedIds, actualIds)
    }

    @Test
    fun `Exists by Id`() {
        val projectId = "projectId"
        doReturn(true)
            .`when`(httpClientSpy)
            .get("/started-project-work-token/project/$projectId/exists", Boolean::class.java)

        val exists = serviceClientSpy.existsById(projectId)

        assertTrue(exists)
    }
}