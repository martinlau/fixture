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

import io.fixture.domain.PersistentLogin
import io.fixture.repository.PersistentLoginRepository
import io.fixture.repository.UserRepository
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

[Component(value = "persistentTokenRepository")]
class PersistentTokenRepositoryImpl [Autowired] (

        val persistentLoginRepository: PersistentLoginRepository,

        val userRepository: UserRepository

): PersistentTokenRepository {

    [Transactional]
    public override fun createNewToken(token: PersistentRememberMeToken) {
        val user = userRepository.findOne(token.getUsername())
        if (user != null) {
            val persistentLogin = PersistentLogin()
            persistentLogin.lastUsed = token.getDate()
            persistentLogin.series = token.getSeries()
            persistentLogin.token = token.getTokenValue()
            persistentLogin.user = user

            user.persistentLogins[token.getSeries()] = persistentLogin

            persistentLoginRepository.save(persistentLogin)
            userRepository.save(user)
        }
    }

    [Transactional]
    public override fun updateToken(series: String, tokenValue: String, lastUsed: Date) {
        val persistentLogin = persistentLoginRepository.findOne(series)
        if (persistentLogin != null) {
            persistentLogin.token = tokenValue
            persistentLogin.lastUsed = lastUsed

            persistentLoginRepository.save(persistentLogin)
        }
    }

    [Transactional(readOnly = true)]
    public override fun getTokenForSeries(seriesId: String): PersistentRememberMeToken? = persistentLoginRepository.findOne(seriesId)?.toPersistentRememberMeToken()

    [Transactional]
    public override fun removeUserTokens(username: String) = persistentLoginRepository.deleteAllForUsername(username)

    fun PersistentLogin.toPersistentRememberMeToken(): PersistentRememberMeToken = PersistentRememberMeToken(
            user!!.username,
            series,
            token,
            lastUsed
    )
}
