package io.fixture.web.controller

import kotlin.test.assertEquals
import org.junit.Test

class AdministrationControllerTest {

    val subject = AdministrationController()

    [Test]
    fun testIndex() {

        assertEquals(".administration.index", subject.index())
    }

}
