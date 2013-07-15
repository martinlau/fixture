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

class StaticControllerTest {

    val subject = StaticController()

    [Test]
    fun testIndex() {

        assertEquals(".static.index", subject.index())
    }

    [Test]
    fun testAbout() {

        assertEquals(".static.about", subject.about())
    }

    [Test]
    fun testAccessDenied() {

        assertEquals(".static.access-denied", subject.accessDenied())
    }

    [Test]
    fun testContact() {

        assertEquals(".static.contact", subject.contact())
    }

    [Test]
    fun testLogin() {

        assertEquals(".static.login", subject.login())
    }

    [Test]
    fun testTerms() {

        assertEquals(".static.terms", subject.terms())
    }

    [Test]
    fun testPrivacy() {

        assertEquals(".static.privacy", subject.privacy())
    }

}
