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

package io.fixture.controller

import kotlin.test.assertEquals
import org.junit.Test
import io.fixture.service.RegistrationService
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.ContextConfiguration
import org.springframework.validation.MapBindingResult
import java.util.HashMap
import io.fixture.controller.form.RegistrationForm
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.ObjectError
import io.fixture.repository.UserRepository
import org.subethamail.wiser.Wiser
import org.junit.After
import org.junit.Before
import org.springframework.web.servlet.LocaleResolver
import kotlin.test.fail
import java.util.UUID
import kotlin.test.assertFalse

// TODO Reinstantiate when kotlin > 0.5.998
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-domain.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-repository.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-domain-test.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-messages.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-security.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-service.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-service-test.xml"))
        /*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class RegistrationControllerTest {

    [Autowired]
    var registrationService: RegistrationService? = null

    [Autowired]
    var userRepository: UserRepository? = null

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
    fun testIndex() {
        val subject = RegistrationController(registrationService!!)

        assertEquals(".registration.index", subject.index())
    }

    [Test]
    fun testSubmitWithValidForm() {
        val subject = RegistrationController(registrationService!!)
        val form = RegistrationForm(
                givenName = "given name",
                familyName = "family name",
                email = "email@example.com",
                username = "username",
                password = "password",
                confirm = "password",
                accept = true
        )
        val errors = MapBindingResult(HashMap(), "form")

        val result = subject.submit(form, errors)

        assertEquals("redirect:/register/sent", result)
        assertEquals(7.toLong(), userRepository!!.count())
        assertEquals(1, wiser!!.getMessages().size)
    }

    [Test]
    fun testSubmitWithInvalidForm() {
        val subject = RegistrationController(registrationService!!)
        val form = RegistrationForm()
        val errors = MapBindingResult(HashMap(), "form")
        errors.addError(ObjectError("field", "message"))

        val result = subject.submit(form, errors)

        assertEquals(".registration.index", result)
    }

    [Test]
    fun testSent() {
        val subject = RegistrationController(registrationService!!)

        assertEquals(".registration.sent", subject.sent())
    }

    [Test]
    fun testActivateWithValidToken() {
        val subject = RegistrationController(registrationService!!)
        val token = UUID.fromString("33333333-3333-3333-3333-333333333333")

        val result = subject.activate(token)

        assertEquals(".registration.activated", result)
    }

    [Test(expected = javaClass<RegistrationController.ActivationNotFoundException>())]
    fun testActivateWithInvalidToken() {
        val subject = RegistrationController(registrationService!!)
        val token = UUID.randomUUID()

        subject.activate(token)
    }

}
