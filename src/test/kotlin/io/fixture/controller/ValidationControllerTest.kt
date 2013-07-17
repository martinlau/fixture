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

import kotlin.test.assertEquals
import org.junit.Test
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.validation.MapBindingResult
import java.util.HashMap
import org.springframework.validation.ObjectError
import org.springframework.validation.FieldError

class ValidationControllerTest {

    val subject = ValidationController()

    [Test]
    fun testHandleValidationError() {
        val bindingResult = MapBindingResult(HashMap<Any?, Any?>(), "test")
        bindingResult.addError(ObjectError("objectName", "must be set"))
        bindingResult.addError(ObjectError("objectName", "must be valid"))
        bindingResult.addError(FieldError("objectName", "fieldName", "must not be set"))

        val result = subject.handleValidationError(MethodArgumentNotValidException(null, bindingResult))

        assertEquals(2, result.size)
        assertEquals(result.get("*"), listOf("must be set", "must be valid"))
        assertEquals(result.get("fieldName"), listOf("must not be set"))
    }

}
