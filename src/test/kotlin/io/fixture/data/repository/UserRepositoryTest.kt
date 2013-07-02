package io.fixture.data.repository

import io.fixture.data.model.User
import kotlin.properties.Delegates
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

[ContextHierarchy(
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-data.xml")),
        ContextConfiguration(array("classpath:/META-INF/spring/test/fixture-data.xml"))
)]
[RunWith(javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class UserRepositoryTest {

    [Autowired]
    var groupRepository: GroupRepository? = null

    [Autowired]
    var persistentLoginRepository: PersistentLoginRepository? = null

    [Autowired]
    var subject: UserRepository? = null

    [Test]
    fun testDelete() {
        subject!!.delete("username-0")

        assertEquals(5.toLong(), subject!!.count())
        assertEquals(4.toLong(), groupRepository!!.count())
        assertEquals(6.toLong(), persistentLoginRepository!!.count())
    }

    [Test]
    fun testDeleteWithGroupsAndPersistentLogins() {
        subject!!.delete("username-1")

        assertEquals(5.toLong(), subject!!.count())
        assertEquals(4.toLong(), groupRepository!!.count())
        assertEquals(5.toLong(), persistentLoginRepository!!.count())
    }

    [Test]
    fun testDeleteWithInvalidUsername() {
        subject!!.delete("invalid")

        assertEquals(6.toLong(), subject!!.count())
        assertEquals(4.toLong(), groupRepository!!.count())
        assertEquals(6.toLong(), persistentLoginRepository!!.count())
    }

    [Test]
    fun testExists() {
        val result = subject!!.exists("username-0")

        assertTrue(result)
    }

    [Test]
    fun testExistsWithInvalidUsername() {
        val result = subject!!.exists("invalid")

        assertFalse(result)
    }

    [Test]
    fun testFindOne() {
        val result = subject!!.findOne("username-0")

        assertEquals("username-0", result!!.username)
    }

    [Test]
    fun testFindOneWithInvalidUsername() {
        val result = subject!!.findOne("invalid")

        assertNull(result)
    }

}
