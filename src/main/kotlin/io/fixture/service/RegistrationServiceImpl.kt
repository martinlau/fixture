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

import io.fixture.controller.form.RegistrationForm
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import io.fixture.repository.UserRepository
import io.fixture.domain.User
import io.fixture.domain.UserProfile
import io.fixture.repository.UserProfileRepository
import org.springframework.security.crypto.password.PasswordEncoder
import io.fixture.repository.ActivationRepository
import io.fixture.domain.Activation
import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import java.util.Locale
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

[Service]
class RegistrationServiceImpl [Autowired] (

        val activationRepository: ActivationRepository,

        val mailService: MailService,

        val messageSource: MessageSource,

        val passwordEncoder: PasswordEncoder,

        val userProfileRepository: UserProfileRepository,

        val userRepository: UserRepository

): RegistrationService {

    [Value(value = "\${fixture.service.registration.baseUrl}")]
    var baseUrl: String? = null

    [Transactional]
    override fun register(form: RegistrationForm) {

        val user = userRepository.save(User(
                accountNonExpired = true,
                credentialsNonExpired = true,
                enabled = false,
                password = passwordEncoder.encode(form.password!!),
                username = form.username!!
        ))

        val profile = userProfileRepository.save(UserProfile(
                givenName = form.givenName!!,
                familyName = form.familyName!!,
                email = form.email!!,
                user = user
        ))

        val activation = activationRepository.save(Activation(user))

        val subject = messageSource.getMessage("registration.email.subject", array<Any>(), LocaleContextHolder.getLocale())!!

        mailService.sendMail(
                "registration-activation.vm",
                profile!!.email,
                "${profile.givenName} ${profile.familyName}",
                subject,
                mapOf(
                        Pair("activation", activation!!),
                        Pair("baseUrl", baseUrl!!),
                        Pair("user", user!!),
                        Pair("profile", profile!!)
                )
        )
    }

    [Transactional]
    override fun activate(token: UUID): Boolean {

        val activation = activationRepository.findOne(token)
        if (activation == null) return false

        val user = activation.user
        if (user == null) return false

        user.enabled = true
        user.accountNonLocked = true

        userRepository.save(user)
        activationRepository.delete(activation)

        return true
    }

}
