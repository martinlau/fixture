package io.fixture.repository

import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

// TODO Reinstantiate when kotlin > 0.5.748
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-domain.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-repository.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-domain-test.xml"))
/*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class PersistentLoginRepositoryTest {

    [Autowired]
    var subject: PersistentLoginRepository? = null

    [Test]
    fun testDeleteAllForUsernameForUnknownUser() {
        subject!!.deleteAllForUsername("unknown")

        assertEquals(6.toLong(), subject!!.count())
    }

    [Test]
    fun testDeleteAllForUsernameForUserWithMultiplePersistentLogins() {
        subject!!.deleteAllForUsername("username-2")

        assertEquals(4.toLong(), subject!!.count())
    }

    [Test]
    fun testDeleteAllForUsernameForUserWithNoPersistentLogins() {
        subject!!.deleteAllForUsername("username-0")

        assertEquals(6.toLong(), subject!!.count())
    }

    [Test]
    fun testDeleteAllForUsernameForUserWithOnePersistentLogin() {
        subject!!.deleteAllForUsername("username-1")

        assertEquals(5.toLong(), subject!!.count())
    }

    [Test]
    fun testFindOne() {
        val result = subject!!.findOne("----series-1----")

        assertEquals("----series-1----", result!!.series)
    }

    [Test]
    fun testFindOneWithInvalidSeries() {
        val result = subject!!.findOne("invalid")

        assertNull(result)
    }

}
