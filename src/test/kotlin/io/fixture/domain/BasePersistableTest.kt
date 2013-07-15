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

package io.fixture.domain

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
