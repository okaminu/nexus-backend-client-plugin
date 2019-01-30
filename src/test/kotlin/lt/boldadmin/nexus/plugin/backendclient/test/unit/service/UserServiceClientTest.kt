package lt.boldadmin.nexus.plugin.backendclient.test.unit.service

import com.nhaarman.mockito_kotlin.doReturn
import lt.boldadmin.nexus.api.type.entity.User
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient
import lt.boldadmin.nexus.plugin.backendclient.service.UserServiceClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertSame
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class UserServiceClientTest {

    @Mock
    private lateinit var httpClientSpy: BackendHttpClient

    private lateinit var userServiceClient: UserServiceClient

    @Before
    fun setUp() {
        userServiceClient = UserServiceClient(httpClientSpy)
    }

    @Test
    fun `Exists any user`() {
        doReturn(true).`when`(httpClientSpy).get("/user/exists-any", Boolean::class.java)

        val exists = userServiceClient.existsAny()

        assertTrue(exists)
    }

    @Test
    fun `Creates with defaults`() {
        val expectedUser = User()
        doReturn(expectedUser).`when`(httpClientSpy).get("/user/create-with-defaults", User::class.java)

        val actualUser = userServiceClient.createWithDefaults()

        assertSame(expectedUser, actualUser)
    }
}