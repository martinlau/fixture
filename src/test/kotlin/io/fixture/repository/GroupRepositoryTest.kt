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

package io.fixture.repository

import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

// TODO Reinstantiate when kotlin > 0.5.1131
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath*:/META-INF/spring/*.xml")),
        ContextConfiguration(value = array("classpath*:/META-INF/spring/test/*.xml"))
/*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class GroupRepositoryTest {

    [Autowired]
    var subject: GroupRepository? = null

    [Autowired]
    var userRepository: UserRepository? = null

    [Test]
    fun testDelete() {
        subject!!.delete("name-0")

        assertEquals(3.toLong(), subject!!.count())
        assertEquals(6.toLong(), userRepository!!.count())
    }

    [Test]
    fun testDeleteWithInvalidName() {
        subject!!.delete("invalid")

        assertEquals(4.toLong(), subject!!.count())
        assertEquals(6.toLong(), userRepository!!.count())
    }

    [Test]
    fun testDeleteWithUsers() {
        subject!!.delete("name-1")

        assertEquals(3.toLong(), subject!!.count())
        assertEquals(6.toLong(), userRepository!!.count())
    }

    [Test]
    fun testFindAllNames() {
        val result = subject!!.findAllNames()

        assertEquals(4, result.size)
        assertTrue(result.containsItem("name-0"))
        assertTrue(result.containsItem("name-1"))
        assertTrue(result.containsItem("name-2"))
        assertTrue(result.containsItem("name-3"))
    }

    [Test]
    fun testFindAllUsernamesWithInvalidName() {
        val result = subject!!.findAllUsernames("invalid")

        assertTrue(result.isEmpty())
    }

    [Test]
    fun testFindAllUsernamesWithMultipleUsers() {
        val result = subject!!.findAllUsernames("name-2")

        assertEquals(2, result.size)
        assertTrue(result.containsItem("username-2"))
        assertTrue(result.containsItem("username-3"))
    }

    [Test]
    fun testFindAllUsernamesWithSingleUser() {
        val result = subject!!.findAllUsernames("name-1")

        assertEquals(1, result.size)
        assertTrue(result.containsItem("username-1"))
    }

    [Test]
    fun testFindOne() {
        val result = subject!!.findOne("name-0")

        assertEquals("name-0", result!!.name)
    }

    [Test]
    fun testFindOneWithInvalidName() {
        val result = subject!!.findOne("invalid")

        assertNull(result)
    }

}
