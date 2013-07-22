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
[Table(name = "users")]
class User(

        [Column(
                name = "account_non_expired",
                nullable = false
        )]
        var accountNonExpired: Boolean = false,

        [Column(
                name = "account_non_locked",
                nullable = false
        )]
        var accountNonLocked: Boolean = false,

        [CollectionTable(
                name = "user_account_tokens",
                joinColumns = array(JoinColumn(name = "user_uuid"))
        )]
        [Column(name = "token")]
        [ElementCollection(targetClass = javaClass<UUID>())]
        [MapKeyClass(value = javaClass<User.TokenType>())]
        [MapKeyColumn(
                name = "token_type",
                unique = true
        )]
        [MapKeyEnumerated(value = EnumType.STRING)]
        [Type(`type` = "uuid-char")]
        var accountTokens: MutableMap<User.TokenType, UUID> = HashMap(),

        [OneToOne(
                cascade = array(CascadeType.ALL),
                mappedBy = "user",
                targetEntity = javaClass<Activation>()
        )]
        var activation: Activation? = null,

        [CollectionTable(
                name = "user_authorities",
                joinColumns = array(JoinColumn(name = "user_uuid"))
        )]
        [Column(
                name = "authority",
                nullable = false
        )]
        [ElementCollection(targetClass = javaClass<String>())]
        var authorities: MutableSet<String> = HashSet(),

        [Column(
                name = "credentials_non_expired",
                nullable = false
        )]
        var credentialsNonExpired: Boolean = false,

        [Column(
                name = "enabled",
                nullable = false
        )]
        var enabled: Boolean = false,

        [ManyToMany(
                mappedBy = "users",
                targetEntity = javaClass<Group>()
        )]
        var groups: MutableSet<Group> = HashSet(),

        [Column(
                name = "password",
                nullable = false
        )]
        [NotNull]
        var password: String = "",

        [OneToMany(
                mappedBy = "user",
                targetEntity = javaClass<PersistentLogin>()
        )]
        [MapKeyClass(value = javaClass<String>())]
        [MapKeyColumn(name = "series")]
        var persistentLogins: MutableMap<String, PersistentLogin> = HashMap(),

        [OneToOne(
                cascade = array(CascadeType.ALL),
                mappedBy = "user",
                targetEntity = javaClass<UserProfile>()
        )]
        var profile: UserProfile? = null,

        [Column(
                name = "username",
                nullable = false,
                unique = true
        )]
        [Length(
                min = 3,
                max = 64
        )]
        [NotNull]
        var username: String = ""

        ): BasePersistable() {

    enum class TokenType {
        ACTIVATION
        PASSWORD_RESET
    }

}
