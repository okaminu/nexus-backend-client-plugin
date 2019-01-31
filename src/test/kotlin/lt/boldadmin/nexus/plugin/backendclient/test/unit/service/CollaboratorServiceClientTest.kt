package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Collaborator
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.CollaboratorServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class CollaboratorServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var serviceClientSpy: CollaboratorServiceClient

    @Before
    fun setUp() {
        serviceClientSpy = CollaboratorServiceClient(httpClientSpy)
    }

    @Test
    fun `Updates attribute`() {
        val attributeName = "attributeName"
        val attributeValue = "attributeValue"
        val collaboratorId = "collaboratorId"

        serviceClientSpy.update(collaboratorId, attributeName, attributeValue)

        verify(httpClientSpy).post("/collaborator/$collaboratorId/attribute/$attributeName/update", attributeValue)
    }

    @Test
    fun `Updates order number`() {
        val orderNumber: Short = 5
        val collaboratorId = "collaboratorId"

        serviceClientSpy.updateOrderNumber(collaboratorId, orderNumber)

        verify(httpClientSpy).post("/collaborator/$collaboratorId/attribute/order-number/update", orderNumber)
    }

    @Test
    fun `Creates collaborator with defaults`() {
        val expectedCollaborator = Collaborator()
        doReturn(expectedCollaborator).`when`(httpClientSpy).get("/collaborator/create-with-defaults", Collaborator::class.java)

        val actualCollaborator = serviceClientSpy.createWithDefaults()

        assertSame(expectedCollaborator, actualCollaborator)
    }

    @Test
    fun `Gets collaborator by id`() {
        val expectedCollaborator = Collaborator()
        val collaboratorId = "collaboratorId"
        doReturn(expectedCollaborator).`when`(httpClientSpy).get("/collaborator/$collaboratorId", Collaborator::class.java)

        val actualCollaborator = serviceClientSpy.getById(collaboratorId)

        assertSame(expectedCollaborator, actualCollaborator)
    }
}