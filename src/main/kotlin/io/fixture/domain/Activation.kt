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

package io.fixture.domain

import java.util.HashMap
import java.util.HashSet
import java.util.UUID
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.EnumType
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.MapKeyClass
import javax.persistence.MapKeyColumn
import javax.persistence.MapKeyEnumerated
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.CascadeType
import org.hibernate.annotations.Type
import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

[Entity]
[Table(name = "activations")]
class Activation(

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

): BasePersistable()

