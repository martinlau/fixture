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

package io.fixture.repository

import io.fixture.domain.Group
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.persistence.QueryHint
import org.springframework.data.jpa.repository.QueryHints

trait GroupRepository: JpaRepository<Group, UUID> {

    [Modifying]
    [Query(value = "DELETE FROM Group g WHERE g.name = :name")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun delete([Param(value = "name")] name: String)

    [Query(value = "SELECT g.name FROM Group g")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findAllNames(): List<String>

    [Query(value = "SELECT u.username FROM User u INNER JOIN u.groups g WHERE g.name = :name")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findAllUsernames([Param(value = "name")] name: String): List<String>

    [Query(value = "SELECT g FROM Group g WHERE g.name = :name")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findOne([Param(value = "name")] name: String): Group?

}
