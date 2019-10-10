package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.api.type.valueobject.Location
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.ProjectServiceClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ProjectServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var projectServiceClient: ProjectServiceClient

    @BeforeEach
    fun setUp() {
        projectServiceClient = ProjectServiceClient(httpClientSpy)
    }

    @Test
    fun `Updates attribute`() {
        val attributeName = "attributeName"
        val attributeValue = "attributeValue"
        val projectId = "projectId"

        projectServiceClient.update(projectId, attributeName, attributeValue)

        verify(httpClientSpy).post("/project/$projectId/attribute/$attributeName/update", attributeValue)
    }

    @Test
    fun `Updates order number`() {
        val orderNumber: Short = 5
        val projectId = "projectId"

        projectServiceClient.updateOrderNumber(projectId, orderNumber)

        verify(httpClientSpy).post("/project/$projectId/attribute/order-number/update", orderNumber)
    }

    @Test
    fun `Updates location`() {
        val location = Location(Coordinates(0.0, 0.0))
        val projectId = "projectId"

        projectServiceClient.updateLocation(projectId, location)

        verify(httpClientSpy).postAsJson("/project/$projectId/attribute/location/update", location)
    }

    @Test
    fun `Creates project with defaults`() {
        val expectedProject = Project()
        val userId = "userId"
        doReturn(expectedProject)
            .`when`(httpClientSpy)
            .get("/project/user/$userId/create-with-defaults", Project::class.java)

        val actualProject = projectServiceClient.createWithDefaults(userId)

        assertSame(expectedProject, actualProject)
    }

    @Test
    fun `Gets project by id`() {
        val expectedProject = Project()
        val projectId = "projectId"
        doReturn(expectedProject).`when`(httpClientSpy).get("/project/$projectId", Project::class.java)

        val actualProject = projectServiceClient.getById(projectId)

        assertSame(expectedProject, actualProject)
    }
}
