package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Project
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.api.type.valueobject.Location
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.ProjectServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class ProjectServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var projectServiceClient: ProjectServiceClient

    @Before
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
    fun `Deletes location`() {
        val projectId = "projectId"

        projectServiceClient.deleteLocation(projectId)

        verify(httpClientSpy).delete("/project/$projectId/attribute/location")
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
