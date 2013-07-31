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

package io.fixture.controller.form

import org.junit.Test
import org.springframework.web.client.RestTemplate
import io.fixture.controller.form.RegistrationForm
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.runner.RunWith
import org.springframework.http.HttpMethod
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import kotlin.test.assertEquals
import org.springframework.test.context.ContextConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.util.MultiValueMap
import org.springframework.util.LinkedMultiValueMap
import org.junit.Before
import kotlin.test.assertTrue
import org.junit.After

class RegistrationFormIT {

    val baseUri = "http://localhost:" + System.getProperty("tomcat.http.port") + "/fixture"

    val restTemplate = RestTemplate()

    val headers = LinkedMultiValueMap<String, String>()

    val form = RegistrationForm(
            givenName = "given name",
            familyName = "family name",
            email = "email@example.com",
            username = "username",
            password = "password",
            confirm = "password",
            accept = true
    )

    [Before]
    fun before() {
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE)
    }

    [After]
    fun after() {
        headers.clear()
    }

    [Test]
    fun testValidateRegistrationForm() {
        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertTrue(result!!.empty)
    }

    [Test]
    fun testValidateRegistrationFormWithoutGivenName() {
        form.givenName = null

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(1, result!!.size)
        assertEquals(listOf("may not be empty"), result.get("givenName"))
    }

    [Test]
    fun testValidateRegistrationFormWithoutFamilyName() {
        form.familyName = null

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(1, result!!.size)
        assertEquals(listOf("may not be empty"), result.get("familyName"))
    }

    [Test]
    fun testValidateRegistrationFormWithoutEmail() {
        form.email = null

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(1, result!!.size)
        assertEquals(listOf("may not be empty"), result.get("email"))
    }

    [Test]
    fun testValidateRegistrationFormWithInvalidEmail() {
        form.email = "invalid"

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(1, result!!.size)
        assertEquals(listOf("not a well-formed email address"), result.get("email"))
    }

    [Test]
    fun testValidateRegistrationFormWithoutUsername() {
        form.username = null

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(1, result!!.size)
        assertEquals(listOf("may not be empty"), result.get("username"))
    }

    [Test]
    fun testValidateRegistrationFormWithoutPassword() {
        form.password = null

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(2, result!!.size)
        assertEquals(listOf("may not be empty"), result.get("password"))
        assertEquals(listOf("""script expression "_this.password == _this.confirm" didn't evaluate to true"""), result.get("*"))
    }

    [Test]
    fun testValidateRegistrationFormWithoutConfirm() {
        form.confirm = null

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(2, result!!.size)
        assertEquals(listOf("""script expression "_this.password == _this.confirm" didn't evaluate to true"""), result.get("*"))
        assertEquals(listOf("may not be empty"), result.get("confirm"))
    }

    [Test]
    fun testValidateRegistrationFormWithMismatchedConfirm() {
        form.password = "password"
        form.confirm = "different"

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(1, result!!.size)
        assertEquals(listOf("""script expression "_this.password == _this.confirm" didn't evaluate to true"""), result.get("*"))
    }

    [Test]
    fun testValidateRegistrationFormWithoutAccept() {
        form.accept = null

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(1, result!!.size)
        assertEquals(listOf("may not be null"), result.get("accept"))
    }

    [Test]
    fun testValidateRegistrationFormWithInvalidAccept() {
        form.accept = false

        val result = restTemplate.exchange(
                "${baseUri}/validate/registration",
                HttpMethod.POST,
                HttpEntity<RegistrationForm>(form, headers),
                javaClass<Map<Any?, Any?>>()
        )!!.getBody()

        assertEquals(1, result!!.size)
        assertEquals(listOf("must be true"), result.get("accept"))
    }


}
