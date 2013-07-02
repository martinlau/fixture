package io.fixture.web.controller

import kotlin.test.assertEquals
import org.junit.Test

class SecureControllerTest {

    val subject = SecureController()

    [Test]
    fun testIndex() {

        assertEquals(".secure.index", subject.index())
    }

}
