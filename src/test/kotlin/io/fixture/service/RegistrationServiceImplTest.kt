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

package io.fixture.service

import org.springframework.transaction.annotation.Transactional
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.junit.Test
import kotlin.test.fail
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID
import kotlin.test.assertFalse
import io.fixture.repository.UserRepository
import kotlin.test.assertTrue
import io.fixture.form.RegistrationForm
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.subethamail.wiser.Wiser

// TODO Reinstantiate when kotlin > 0.5.998
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath*:/META-INF/spring/*.xml")),
        ContextConfiguration(value = array("classpath*:/META-INF/spring/test/*.xml"))
        /*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class RegistrationServiceImplTest {

    [Autowired]
    var subject: RegistrationService?  = null

    [Autowired]
    var userRepository: UserRepository? = null

    [Autowired]
    val wiser: Wiser? = null

    [Test]
    fun testRegister() {
        val form = RegistrationForm(
                givenName = "given name",
                familyName = "family name",
                email = "email@example.com",
                username = "username",
                password = "password",
                confirm = "password",
                accept = true
        )

        subject!!.register(form)

        val user = userRepository!!.findOne("username")!!
        val profile = user.profile!!
        assertTrue(user.accountNonExpired)
        assertTrue(user.accountNonLocked)
        assertTrue(user.credentialsNonExpired)
        assertFalse(user.enabled)
        assertNotNull(user.password)
        assertEquals("username", user.username)

        assertEquals("given name", profile.givenName)
        assertEquals("family name", profile.familyName)
        assertEquals("email@example.com", profile.email)

        assertEquals(1, wiser!!.getMessages().size)
        val message = wiser.getMessages()[0].toString()!!

        assertTrue(message.contains("""Sender: "fixture.io" <us@fixture.io>"""))
        assertTrue(message.contains("""To: given name family name <email@example.com>"""))
        assertTrue(message.contains("""Subject: Fixture Account Registration"""))
        assertTrue(message.contains("""Hi given name,"""))
        assertTrue(message.contains("""/fixture/registration/activate/${user.uuid}"""))
    }

    [Test]
    fun testActivate() {
        val token = UUID.fromString("33333333-3333-3333-3333-333333333333")

        val result = subject!!.activate(token)

        assertTrue(result)
        assertTrue(userRepository!!.findOne(token)!!.enabled)
    }

    [Test]
    fun testActivateWithInvalidToken() {
        val token = UUID.randomUUID()

        val result = subject!!.activate(token)

        assertFalse(result)
    }

}
