package io.fixture.web.interceptor

import io.fixture.web.interceptor.test.MockThemeResolver
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
