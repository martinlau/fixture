/*
 * #%L
 * fixture
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package io.fixture.security

import io.fixture.domain.User
import io.fixture.repository.UserRepository
import io.fixture.security.test.MockAuthenticationManager
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.provisioning.UserDetailsManager

// TODO Reinstantiate when kotlin > 0.5.1131
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath*:/META-INF/spring/*.xml")),
        ContextConfiguration(value = array("classpath*:/META-INF/spring/test/*.xml"))
/*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class UserDetailsManagerImplTest {

    [Autowired]
    var authenticationManager: MockAuthenticationManager? = null

    [Autowired]
    var subject: UserDetailsManager? = null

    [Autowired]
    var userRepository: UserRepository? = null

    [Before]
    fun setUp() {
        authenticationManager!!.callCount = 0
    }

    [After]
    fun tearDown() {
        authenticationManager!!.callCount = 0
    }

    [Test(expected = javaClass<AccessDeniedException>())]
    fun testChangePasswordWithoutAuthentication() {
        subject!!.changePassword("password-1", "test-password")
    }

    [Test]
    fun testCreateUser() {
        subject!!.createUser(
                SpringUser(
                        "test-username",
                        "test-password",
                        true,
                        true,
                        true,
                        true,
                        linkedListOf(SimpleGrantedAuthority("test-authority"))
                )
        )

        val result = userRepository!!.findOne("test-username")
        assertEquals(1, result!!.authorities.size)
        assertEquals("test-authority", result.authorities.first())
        assertEquals("test-password", result.password)
        assertEquals("test-username", result.username)
        assertTrue(result.accountNonExpired)
        assertTrue(result.credentialsNonExpired)
        assertTrue(result.accountNonLocked)
        assertTrue(result.enabled)
    }

    [Test]
    fun testDeleteUser() {
        subject!!.deleteUser("username-0")

        assertFalse(userRepository!!.exists("username-0"))
    }

    [Test]
    fun testLoadUserByUsername() {
        val result = subject!!.loadUserByUsername("username-1")

        assertEquals(1, result.getAuthorities().size)
        assertTrue(result.getAuthorities().contains(SimpleGrantedAuthority("authority-1")))
        assertEquals("password-1", result.getPassword())
        assertEquals("username-1", result.getUsername())
        assertTrue(result.isAccountNonExpired())
        assertTrue(result.isCredentialsNonExpired())
        assertTrue(result.isAccountNonLocked())
        assertTrue(result.isEnabled())
    }

    [Test(expected = javaClass<UsernameNotFoundException>())]
    fun testLoadUserByUsernameWithInvalidUsername() {
        subject!!.loadUserByUsername("invalid")
    }

    [Test]
    fun testUpdateUser() {
        subject!!.updateUser(
                SpringUser(
                        "username-1",
                        "test-password",
                        true,
                        true,
                        true,
                        true,
                        linkedListOf(SimpleGrantedAuthority("test-authority"))
                )
        )

        val result = userRepository!!.findOne("username-1")
        assertEquals(1, result!!.authorities.size)
        assertTrue(result.authorities.contains("test-authority"))
        assertEquals("test-password", result.password)
        assertEquals("username-1", result.username)
        assertTrue(result.accountNonExpired)
        assertTrue(result.credentialsNonExpired)
        assertTrue(result.accountNonLocked)
        assertTrue(result.enabled)
    }

    [Test]
    fun testUserExists() {
        val result = subject!!.userExists("username-0")

        assertTrue(result)
    }

    [Test]
    fun testUserExistsWithInvalidUsername() {
        val result = subject!!.userExists("invalid")

        assertFalse(result)
    }

}
