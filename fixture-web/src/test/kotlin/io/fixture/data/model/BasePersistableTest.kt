package io.fixture.data.model

import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BasePersistableTest {

    val subject = BasePersistable()

    [Test]
    fun testGetId() {
        val uuid = UUID.randomUUID()
        subject.uuid = uuid

        val result = subject.getId()

        assertEquals(uuid, result)
    }

    [Test]
    fun testIsNewWithVersion() {
        subject.version = 1234

        val result = subject.isNew()

        assertFalse(result)
    }

    [Test]
    fun testIsNewWithoutVersion() {
        subject.version = null

        val result = subject.isNew()

        assertTrue(result)
    }

}
