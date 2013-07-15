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

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.Past
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull

[Entity]
[Table(name = "persistent_logins")]
public class PersistentLogin: BasePersistable() {

    [Column(
            name = "last_used",
            nullable = false
    )]
    [NotNull]
    [Past]
    [Temporal(value = TemporalType.TIMESTAMP)]
    var lastUsed: Date = Date(0)

    [Column(
            name = "series",
            nullable = false,
            unique = true
    )]
    [Length(
            max = 16,
            min = 16
    )]
    [NotNull]
    var series: String = ""

    [Column(
            name = "token",
            nullable = false
    )]
    [Length(
            max = 16,
            min = 16
    )]
    [NotNull]
    var token = ""

    [JoinColumn(
            name = "user_uuid",
            nullable = false
    )]
    [ManyToOne(targetEntity = javaClass<User>())]
    [NotNull]
    var user: User? = null

}
