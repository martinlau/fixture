package io.fixture.security

import io.fixture.repository.PersistentLoginRepository
import io.fixture.repository.UserRepository
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

[ContextHierarchy(
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-domain.xml")),
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-repository.xml")),
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-domain-test.xml")),
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-security.xml"))
)]
[RunWith(javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
public class PersistentTokenRepositoryImplTest {

    val date = Date()

    [Autowired]
    var persistentLoginRepository: PersistentLoginRepository? = null

    [Autowired]
    var subject: PersistentTokenRepositoryImpl? = null

    [Autowired]
    var userRepository: UserRepository? = null

    [Test]
    fun testCreateNewToken() {
        subject!!.createNewToken(
                PersistentRememberMeToken(
                        "username-0",
                        "test-series",
                        "test-token",
                        date
                )
        )

        val result = userRepository!!.findOne("username-0")
        assertTrue { result!!.persistentLogins["test-series"] != null }
        assertEquals(date, result!!.persistentLogins["test-series"]!!.lastUsed)
    }

    [Test]
    fun testGetTokenForSeries() {
        val result = subject!!.getTokenForSeries("series-1")

        assertEquals("series-1", result!!.getSeries())
    }

    [Test]
    fun testRemoveUserTokens() {
        subject!!.removeUserTokens("username-2")

        val result = userRepository!!.findOne("username-2")
        assertTrue(result!!.persistentLogins.empty)
    }

    [Test]
    fun testUpdateToken() {
        subject!!.updateToken("series-1", "test-token", date)

        val result = persistentLoginRepository!!.findOne("series-1")
        assertEquals("test-token", result!!.token)
        assertEquals(date, result.lastUsed)
    }

}
