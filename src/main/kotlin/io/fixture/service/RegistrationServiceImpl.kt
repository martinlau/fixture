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

package io.fixture.service

import io.fixture.form.RegistrationForm
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import io.fixture.repository.UserRepository
import io.fixture.domain.User
import io.fixture.domain.UserProfile
import io.fixture.repository.UserProfileRepository
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import java.util.Locale
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

[Service(value = "registrationService")]
class RegistrationServiceImpl [Autowired] (

        val mailService: MailService,

        val messageSource: MessageSource,

        val passwordEncoder: PasswordEncoder,

        val userProfileRepository: UserProfileRepository,

        val userRepository: UserRepository,

        [Value(value = "\${fixture.service.registration.baseUrl}")]
        val baseUrl: String

): RegistrationService {

    [Transactional]
    override fun register(form: RegistrationForm) {

        val user = User(
                accountNonExpired = true,
                accountNonLocked = true,
                credentialsNonExpired = true,
                enabled = false,
                password = passwordEncoder.encode(form.password!!),
                username = form.username!!
        )

        val profile = UserProfile(
                givenName = form.givenName!!,
                familyName = form.familyName!!,
                email = form.email!!
        )

        user.profile = profile
        profile.user = user

        userRepository.save(user)
        userProfileRepository.save(profile)

        val subject = messageSource.getMessage("registration.email.subject", array<Any>(), LocaleContextHolder.getLocale())!!

        mailService.sendMail(
                "registration-activation.vm",
                profile!!.email,
                "${profile.givenName} ${profile.familyName}",
                subject,
                mapOf(
                        Pair("baseUrl", baseUrl),
                        Pair("user", user!!),
                        Pair("profile", profile)
                )
        )
    }

    [Transactional]
    override fun activate(token: UUID): Boolean {

        val user = userRepository.findOne(token)
        if (user == null) return false

        user.enabled = true

        userRepository.save(user)

        return true
    }

}
