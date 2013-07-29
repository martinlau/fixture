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

package io.fixture.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ModelAttribute
import io.fixture.controller.form.RegistrationForm
import org.springframework.validation.Errors
import javax.validation.Valid
import org.springframework.web.bind.annotation.RequestMethod
import io.fixture.service.RegistrationService
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable

[Controller(value = "registrationController")]
[RequestMapping(value = array("/register"))]
class RegistrationController [Autowired](

        val registrationService: RegistrationService

) {

    // TODO Register or Registration - pick one

    [ModelAttribute]
    fun registrationForm() = RegistrationForm()

    [RequestMapping(method = array(RequestMethod.GET))]
    fun index() = ".registration.index"

    [RequestMapping(method = array(RequestMethod.POST))]
    fun submit([Valid] form: RegistrationForm, errors: Errors): String {
        if (errors.hasErrors()) {
            return ".registration.index"
        }
        registrationService.register(form)
        return "redirect:/register/sent"
    }

    [RequestMapping(value = array("/sent"))]
    fun sent() = ".registration.sent"

    [RequestMapping(value = array("/activate/{token}"))]
    fun activate([PathVariable(value = "token")] token: UUID) =
            if (registrationService.activate(token)) ".registration.activated"
            else throw ActivationNotFoundException()

    [ResponseStatus(value = HttpStatus.NOT_FOUND)]
    class ActivationNotFoundException: Exception()

}
