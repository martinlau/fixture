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

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.MethodArgumentNotValidException
import java.util.HashMap
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid
import org.springframework.web.bind.annotation.ResponseBody
import io.fixture.controller.form.RegistrationForm
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.http.MediaType
import org.springframework.validation.FieldError
import java.util.LinkedList
import java.util.Collections

[Controller(value = "validationController")]
[RequestMapping(value = array("/validate"))]
class ValidationController {

    val valid = Collections.emptyMap<String, List<String?>>()

    // To test this:
    //    $.ajax({
    //        type: 'POST',
    //        url: '/fixture/validate/register.json',
    //        contentType: 'application/json',
    //        data: JSON.stringify({
    //            "username":"blah",
    //            "password":"blah",
    //            "confirm":"blah",
    //            "familyName":"blah",
    //            "givenName":"blah",
    //            "email":"email@blah.com",
    //            "accept":false
    //        }),
    //        success: function(data) {
    //            console.log(data);
    //        }
    //    });

    [RequestMapping(
            method = array(RequestMethod.POST),
            value = array("/register")
    )]
    [ResponseBody]
    fun validateRegistrationForm([Valid] [RequestBody] form: RegistrationForm) = valid

    [ExceptionHandler]
    [ResponseBody]
    fun handleValidationError(exception: MethodArgumentNotValidException): Map<String, List<String?>> {

        val errors = HashMap<String, MutableList<String?>>()

        exception.getBindingResult().getAllErrors().forEach {

            val message = it.getDefaultMessage()
            val name = if (it is FieldError) it.getField() else "*"

            errors.getOrPut(name) { LinkedList() }.add(message)
        }

        return errors
    }

}
