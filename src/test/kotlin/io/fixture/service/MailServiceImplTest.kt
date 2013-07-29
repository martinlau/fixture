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

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.beans.factory.annotation.Autowired
import org.subethamail.wiser.Wiser
import kotlin.test.fail
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.After
import org.junit.Before

// TODO Reinstantiate when kotlin > 0.5.998
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath*:/META-INF/spring/*.xml")),
        ContextConfiguration(value = array("classpath*:/META-INF/spring/test/*.xml"))
/*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
class MailServiceImplTest {

    [Autowired]
    var mailService: MailService? = null

    [Autowired]
    var wiser: Wiser? = null

    [Before]
    fun before() {
        wiser!!.getMessages().clear()
    }

    [After]
    fun after() {
        wiser!!.getMessages().clear()
    }

    [Test]
    fun testSendMail() {
        mailService!!.sendMail(
                "test-send-mail.vm",
                "user@example.com",
                "Test User",
                "Test email",
                mapOf(Pair("link", "http://www.example.com"))
        )

        assertEquals(1, wiser!!.getMessages().size)
        val message = wiser!!.getMessages()[0].toString()!!
        assertTrue(message.contains("""Sender: "fixture.io" <us@fixture.io>"""))
        assertTrue(message.contains("""To: Test User <user@example.com>"""))
        assertTrue(message.contains("A link to http://www.example.com."))
    }

    [Test]
    fun testSendMailWithLocalizedMessage() {
        mailService!!.sendMail(
                "test-send-mail-with-localized-message.vm",
                "user@example.com",
                "Test User",
                "Test email",
                mapOf(Pair("link", "http://www.example.com"))
        )

        assertEquals(1, wiser!!.getMessages().size)
        val message = wiser!!.getMessages()[0].toString()!!
        assertTrue(message.contains("""Sender: "fixture.io" <us@fixture.io>"""))
        assertTrue(message.contains("""To: Test User <user@example.com>"""))
        assertTrue(message.contains("A link to http://www.example.com."))
        assertTrue(message.contains("registration.email.subject=Fixture Account Registration"))
    }

}
