/*
 * Copyright 2013 Martin Lau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fixture.interceptor

import io.fixture.interceptor.test.MockThemeResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Before
import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.servlet.DispatcherServlet

class NextThemeInterceptorTest {

    val subject = NextThemeInterceptor()

    var request: HttpServletRequest? = null

    var response: HttpServletResponse? = null

    val handler = Object()

    [Before]
    fun setUp() {
        request = MockHttpServletRequest()
        response = MockHttpServletResponse()
    }

    [Test]
    fun testPreHandleWithNullThemeResolver() {
        val result = subject.preHandle(request!!, response!!, handler)

        assertTrue(result)
    }

    [Test]
    fun testPreHandleWithNullThemeName() {
        request!!.setAttribute(DispatcherServlet.THEME_RESOLVER_ATTRIBUTE, MockThemeResolver(null))
        val result = subject.preHandle(request!!, response!!, handler)

        assertTrue(result)
    }

    [Test]
    fun testPreHandle() {
        request!!.setAttribute(DispatcherServlet.THEME_RESOLVER_ATTRIBUTE, MockThemeResolver("amelia"))

        subject.preHandle(request!!, response!!, handler)

        assertEquals("cerulean", request!!.getAttribute("nextTheme"))
    }

    [Test]
    fun testPreHandleWithLastTheme() {
        request!!.setAttribute(DispatcherServlet.THEME_RESOLVER_ATTRIBUTE, MockThemeResolver("united"))

        subject.preHandle(request!!, response!!, handler)

        assertEquals("amelia", request!!.getAttribute("nextTheme"))
    }

}
