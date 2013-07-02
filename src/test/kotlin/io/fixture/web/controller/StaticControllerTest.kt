package io.fixture.web.controller

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
