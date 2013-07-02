package io.fixture.data.model

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
open class BasePersistable: Persistable<UUID> {

    [Column(name = "uuid")]
    [GeneratedValue(generator = "uuid2")]
    [GenericGenerator(
            name = "uuid2",
            strategy = "uuid2"
    )]
    [Id]
    [Type(`type` = "uuid-char")]
    var uuid: UUID? = null

    [Column(name = "version")]
    [Version]
    var version: Int? = null

    override fun getId() = uuid

    override fun isNew() = version == null

}
