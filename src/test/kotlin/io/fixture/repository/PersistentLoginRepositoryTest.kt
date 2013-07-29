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

// TODO Reinstantiate when kotlin > 0.5.998
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath*:/META-INF/spring/*.xml")),
        ContextConfiguration(value = array("classpath*:/META-INF/spring/test/*.xml"))
/*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
class PersistentLoginRepositoryTest {

    [Autowired]
    var subject: PersistentLoginRepository? = null

    [Test]
    fun testDeleteAllForUsernameForUnknownUser() {
        subject!!.deleteAllForUsername("unknown")

        assertEquals(6.toLong(), subject!!.count())
    }

    [Test]
    fun testDeleteAllForUsernameForUserWithMultiplePersistentLogins() {
        subject!!.deleteAllForUsername("username-2")

        assertEquals(4.toLong(), subject!!.count())
    }

    [Test]
    fun testDeleteAllForUsernameForUserWithNoPersistentLogins() {
        subject!!.deleteAllForUsername("username-0")

        assertEquals(6.toLong(), subject!!.count())
    }

    [Test]
    fun testDeleteAllForUsernameForUserWithOnePersistentLogin() {
        subject!!.deleteAllForUsername("username-1")

        assertEquals(5.toLong(), subject!!.count())
    }

    [Test]
    fun testFindOne() {
        val result = subject!!.findOne("----series-1----")

        assertEquals("----series-1----", result!!.series)
    }

    [Test]
    fun testFindOneWithInvalidSeries() {
        val result = subject!!.findOne("invalid")

        assertNull(result)
    }

}
