package io.fixture.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

[Entity]
[Table(name = "user_profiles")]
public class UserProfile: BasePersistable() {

    [Column(
            name = "given_name",
            nullable = false
    )]
    var givenName: String = ""

    [Column(
            name = "family_name",
            nullable = false
    )]
    var familyName: String = ""

    [Column(
            name = "email",
            nullable = false,
            unique = true
    )]
    var email: String = ""

    [JoinColumn(
            name = "user_uuid",
            nullable = false
    )]
    [OneToOne(
            optional = false,
            targetEntity = javaClass<User>()
    )]
    var user: User? = null

}
