package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import lt.boldadmin.nexus.api.type.entity.Customer
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.CustomerServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame

@RunWith(MockitoJUnitRunner::class)
class CustomerServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var customerServiceClient: CustomerServiceClient

    @Before
    fun setUp() {
        customerServiceClient = CustomerServiceClient(httpClientSpy)
    }

    @Test
    fun `Updates attribute`() {
        val attributeName = "attributeName"
        val attributeValue = "attributeValue"
        val customerId = "customerId"

        customerServiceClient.update(customerId, attributeName, attributeValue)

        verify(httpClientSpy).post("/customer/$customerId/attribute/$attributeName/update", attributeValue)
    }

    @Test
    fun `Updates order number`() {
        val orderNumber: Short = 5
        val customerId = "customerId"

        customerServiceClient.updateOrderNumber(customerId, orderNumber)

        verify(httpClientSpy).post("/customer/$customerId/attribute/order-number/update", orderNumber)
    }

    @Test
    fun `Creates customer with defaults`() {
        val expectedCustomer = Customer()
        val userId = "userId"
        doReturn(expectedCustomer)
            .`when`(httpClientSpy)
            .get("/customer/user/$userId/create-with-defaults", Customer::class.java)

        val actualCustomer = customerServiceClient.createWithDefaults(userId)

        assertSame(expectedCustomer, actualCustomer)
    }

    @Test
    fun `Gets customer by id`() {
        val expectedCustomer = Customer()
        val customerId = "customerId"
        doReturn(expectedCustomer).`when`(httpClientSpy).get("/customer/$customerId", Customer::class.java)

        val actualCustomer = customerServiceClient.getById(customerId)

        assertSame(expectedCustomer, actualCustomer)
    }

    @Test
    fun `Saves customer`() {
        val customer = Customer()

        customerServiceClient.save(customer)

        verify(httpClientSpy).postAsJson("/customer/save", customer)
    }
}