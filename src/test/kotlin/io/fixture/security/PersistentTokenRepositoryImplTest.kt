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

package io.fixture.security

import io.fixture.repository.PersistentLoginRepository
import io.fixture.repository.UserRepository
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository

// TODO Reinstantiate when kotlin > 0.5.998
[ContextHierarchy(/*value = array(*/
        ContextConfiguration(value = array("classpath*:/META-INF/spring/*.xml")),
        ContextConfiguration(value = array("classpath*:/META-INF/spring/test/*.xml"))
/*)*/)]
[RunWith(value = javaClass<SpringJUnit4ClassRunner>())]
[Transactional]
public class PersistentTokenRepositoryImplTest {

    val date = Date()

    [Autowired]
    var persistentLoginRepository: PersistentLoginRepository? = null

    [Autowired]
    var subject: PersistentTokenRepository? = null

    [Autowired]
    var userRepository: UserRepository? = null

    [Test]
    fun testCreateNewToken() {
        subject!!.createNewToken(
                PersistentRememberMeToken(
                        "username-0",
                        "--test--series--",
                        "---test-token---",
                        date
                )
        )

        val result = userRepository!!.findOne("username-0")
        assertTrue { result!!.persistentLogins["--test--series--"] != null }
        assertEquals(date, result!!.persistentLogins["--test--series--"]!!.lastUsed)
    }

    [Test]
    fun testGetTokenForSeries() {
        val result = subject!!.getTokenForSeries("----series-1----")

        assertEquals("----series-1----", result!!.getSeries())
    }

    [Test]
    fun testRemoveUserTokens() {
        subject!!.removeUserTokens("username-2")

        val result = userRepository!!.findOne("username-2")
        assertTrue(result!!.persistentLogins.empty)
    }

    [Test]
    fun testUpdateToken() {
        subject!!.updateToken("----series-1----", "---test-token---", date)

        val result = persistentLoginRepository!!.findOne("----series-1----")
        assertEquals("---test-token---", result!!.token)
        assertEquals(date, result.lastUsed)
    }

}
