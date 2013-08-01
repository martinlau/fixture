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

package io.fixture.form

import org.hibernate.validator.constraints.ScriptAssert
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull
import javax.validation.constraints.AssertTrue

[ScriptAssert(
        lang = "javascript",
        script = "_this.password == _this.confirm"
)]
[data]
class RegistrationForm(

        [NotEmpty]
        var givenName: String? = null,

        [NotEmpty]
        var familyName: String? = null,

        [Email]
        [NotEmpty]
        var email: String? = null,

        [NotEmpty]
        var username: String? = null,

        [NotEmpty]
        var password: String? = null,

        [NotEmpty]
        var confirm: String? = null,

        [AssertTrue]
        [NotNull]
        var accept: Boolean? = null

)
