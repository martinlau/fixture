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

import io.fixture.domain.User
import kotlin.properties.Delegates
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

// TODO Reinstantiate when kotlin > 0.5.998
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-domain.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-repository.xml")),
        ContextConfiguration(value = array("classpath:/META-INF/spring/fixture-domain-test.xml"))
/*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class UserRepositoryTest {

    [Autowired]
    var groupRepository: GroupRepository? = null

    [Autowired]
    var persistentLoginRepository: PersistentLoginRepository? = null

    [Autowired]
    var subject: UserRepository? = null

    [Test]
    fun testDelete() {
        subject!!.delete("username-0")

        assertEquals(5.toLong(), subject!!.count())
        assertEquals(4.toLong(), groupRepository!!.count())
        assertEquals(6.toLong(), persistentLoginRepository!!.count())
    }

    [Test]
    fun testDeleteWithGroupsAndPersistentLogins() {
        subject!!.delete("username-1")

        assertEquals(5.toLong(), subject!!.count())
        assertEquals(4.toLong(), groupRepository!!.count())
        assertEquals(5.toLong(), persistentLoginRepository!!.count())
    }

    [Test]
    fun testDeleteWithInvalidUsername() {
        subject!!.delete("invalid")

        assertEquals(6.toLong(), subject!!.count())
        assertEquals(4.toLong(), groupRepository!!.count())
        assertEquals(6.toLong(), persistentLoginRepository!!.count())
    }

    [Test]
    fun testExists() {
        val result = subject!!.exists("username-0")

        assertTrue(result)
    }

    [Test]
    fun testExistsWithInvalidUsername() {
        val result = subject!!.exists("invalid")

        assertFalse(result)
    }

    [Test]
    fun testFindOne() {
        val result = subject!!.findOne("username-0")

        assertEquals("username-0", result!!.username)
    }

    [Test]
    fun testFindOneWithInvalidUsername() {
        val result = subject!!.findOne("invalid")

        assertNull(result)
    }

}
