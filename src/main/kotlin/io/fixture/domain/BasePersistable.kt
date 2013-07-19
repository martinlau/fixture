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

import java.util.UUID
import javax.persistence.Cacheable
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Version
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import org.springframework.data.domain.Persistable

[Cacheable]
[MappedSuperclass]
open class BasePersistable(

        [Column(name = "uuid")]
        [GeneratedValue(generator = "uuid2")]
        [GenericGenerator(
                name = "uuid2",
                strategy = "uuid2"
        )]
        [Id]
        [Type(`type` = "uuid-char")]
        var uuid: UUID? = null,

        [Column(name = "version")]
        [Version]
        var version: Int? = null

): Persistable<UUID> {

    override fun getId() = uuid

    override fun isNew() = version == null

}
