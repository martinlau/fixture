/*
 * Copyright 2013 Martin Lau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fixture.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Email
import javax.validation.constraints.NotNull

[Entity]
[Table(name = "user_profiles")]
public class UserProfile: BasePersistable() {

    [Column(
            name = "given_name",
            nullable = false
    )]
    [NotEmpty]
    var givenName: String = ""

    [Column(
            name = "family_name",
            nullable = false
    )]
    [NotEmpty]
    var familyName: String = ""

    [Column(
            name = "email",
            nullable = false,
            unique = true
    )]
    [Email]
    var email: String = ""

    [JoinColumn(
            name = "user_uuid",
            nullable = false
    )]
    [NotNull]
    [OneToOne(
            optional = false,
            targetEntity = javaClass<User>()
    )]
    var user: User? = null

}
