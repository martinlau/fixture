package io.fixture.controller

import kotlin.test.assertEquals
import org.junit.Test

class AdministrationControllerTest {

    val subject = AdministrationController()

    [Test]
    fun testIndex() {

        assertEquals(".administration.index", subject.index())
    }

    [Test]
    fun testUsers() {

        assertEquals(".administration.users", subject.users())
    }

}
