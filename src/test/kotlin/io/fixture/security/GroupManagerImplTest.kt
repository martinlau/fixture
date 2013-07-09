package io.fixture.security

import io.fixture.repository.GroupRepository
import java.util.Arrays
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.context.ContextHierarchy

[ContextHierarchy(
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-domain.xml")),
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-repository.xml")),
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-domain-test.xml")),
        ContextConfiguration(array("classpath:/META-INF/spring/fixture-security.xml"))
)]
[RunWith(javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class GroupManagerImplTest {

    [Autowired]
    var groupRepository: GroupRepository? = null

    [Autowired]
    var subject: GroupManagerImpl? = null

    [Test]
    fun testAddGroupAuthority() {
        subject!!.addGroupAuthority("name-0", SimpleGrantedAuthority("test-authority-1"))

        val result = groupRepository!!.findOne("name-0")

        assertEquals(1, result!!.authorities.size)
        assertEquals("test-authority-1", result.authorities.first())
    }

    [Test]
    fun testAddUserToGroup() {
        subject!!.addUserToGroup("username-0", "name-0")

        val result = groupRepository!!.findOne("name-0")

        assertEquals("username-0", result!!.users.first().username)
    }

    [Test]
    fun testCreateGroup() {
        subject!!.createGroup("test-group", linkedListOf(SimpleGrantedAuthority("test-authority-1"), SimpleGrantedAuthority("test-authority-2")))

        val result = groupRepository!!.findOne("test-group")

        assertEquals("test-group", result!!.name)
        assertEquals(2, result.authorities.size)
        assertTrue(result.authorities.containsAll(linkedListOf("test-authority-1", "test-authority-2")))
    }

    [Test]
    fun testDeleteGroup() {
        subject!!.deleteGroup("name-0")

        assertNull(groupRepository!!.findOne("name-0"))
        assertEquals(3.toLong(), groupRepository!!.count())
    }

    [Test]
    fun testFindAllGroups() {
        val result = subject!!.findAllGroups()

        assertEquals(4, result.size)
        assertTrue(result.containsAll(linkedListOf("name-0", "name-1", "name-2", "name-3")))
    }

    [Test]
    fun testFindGroupAuthorities() {
        val result = subject!!.findGroupAuthorities("name-2")

        assertEquals(2, result.size)
        assertTrue(result.containsAll(linkedListOf(SimpleGrantedAuthority("authority-1"), SimpleGrantedAuthority("authority-2"))))
    }

    [Test]
    fun testFindUsersInGroup() {
        val result = subject!!.findUsersInGroup("name-2")

        assertEquals(2, result.size)
        assertTrue(result.containsAll(linkedListOf("username-2", "username-3")))
    }

    [Test]
    fun testRemoveGroupAuthority() {
        subject!!.removeGroupAuthority("name-1", SimpleGrantedAuthority("authority-1"))

        val result = groupRepository!!.findOne("name-1")

        assertTrue(result!!.authorities.empty)
    }

    [Test]
    fun testRemoveUserFromGroup() {
        subject!!.removeUserFromGroup("username-1", "name-1")

        val result = groupRepository!!.findOne("name-1")

        assertTrue(result!!.users.empty)
    }

    [Test]
    fun testRenameGroup() {
        subject!!.renameGroup("name-3", "test-group")

        assertNull(groupRepository!!.findOne("name-3"))
        assertEquals(UUID.fromString("33333333-3333-3333-3333-333333333333"), groupRepository!!.findOne("test-group")!!.uuid)
    }

}