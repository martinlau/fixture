package io.fixture.domain

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

[Entity]
[Table(name = "persistent_logins")]
public class PersistentLogin: BasePersistable() {

    [Column(
            name = "last_used",
            nullable = false
    )]
    [Temporal(TemporalType.TIMESTAMP)]
    var lastUsed: Date = Date(0)

    [Column(
            name = "series",
            nullable = false,
            unique = true
    )]
    var series: String = ""

    [Column(
            name = "token",
            nullable = false
    )]
    var token = ""

    [JoinColumn(
            name = "user_uuid",
            nullable = false
    )]
    [ManyToOne(targetEntity = javaClass<User>())]
    var user: User? = null

}
