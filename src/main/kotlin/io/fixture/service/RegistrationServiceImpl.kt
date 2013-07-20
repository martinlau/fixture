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

[Service]
class RegistrationServiceImpl [Autowired] (
        val mailService: MailService,
        val passwordEncoder: PasswordEncoder,
        val userProfileRepository: UserProfileRepository,
        val userRepository: UserRepository
): RegistrationService {

    // TODO @Value
    val subject = "Account activation"

    // TODO @Value
    val template = "registration-activation.vm"

    [Transactional]
    override fun register(form: RegistrationForm) {

        val user = User(
                accountNonExpired = true,
                credentialsNonExpired = true,
                enabled = false,
                password = passwordEncoder.encode(form.password!!),
                username = form.username!!
        )

        userRepository.save(user)

        val profile = UserProfile(
                givenName = form.givenName!!,
                familyName = form.familyName!!,
                email = form.email!!,
                user = user
        )
        user.profile = profile

        userProfileRepository.save(profile)

        // TODO Generate token
        val token = ""

        mailService.sendMail(
                template,
                profile.email,
                "${profile.givenName} ${profile.familyName}",
                subject,
                mapOf(
                        Pair("user", user),
                        Pair("profile", profile),
                        Pair("token", token)
                )
        )
    }

}
