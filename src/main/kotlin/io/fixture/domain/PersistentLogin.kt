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
