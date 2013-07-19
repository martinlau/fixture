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

import java.util.HashSet
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Length

[Entity]
[Table(name = "groups")]
class Group(

        [Column(
                name = "authority",
                nullable = false
        )]
        [CollectionTable(
                name = "group_authorities",
                joinColumns = array(JoinColumn(name = "group_uuid"))
        )]
        [ElementCollection(targetClass = javaClass<String>())]
        var authorities: MutableSet<String> = HashSet(),

        [Column(
                name = "name",
                nullable = false,
                unique = true
        )]
        [Length(
                min = 3,
                max = 64
        )]
        [NotEmpty]
        var name: String = "",

        [JoinTable(
                name = "group_members",
                joinColumns = array(JoinColumn(name = "group_uuid")),
                inverseJoinColumns = array(JoinColumn(name = "user_uuid"))
        )]
        [ManyToMany(
                cascade = array(CascadeType.ALL),
                targetEntity = javaClass<User>()
        )]
        var users: MutableSet<User> = HashSet()

): BasePersistable()
