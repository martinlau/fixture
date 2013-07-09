package io.fixture.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Email
import javax.validation.constraints.NotNull

[Entity]
[Table(name = "user_profiles")]
public class UserProfile: BasePersistable() {

    [Column(
            name = "given_name",
            nullable = false
    )]
    [NotEmpty]
    var givenName: String = ""

    [Column(
            name = "family_name",
            nullable = false
    )]
    [NotEmpty]
    var familyName: String = ""

    [Column(
            name = "email",
            nullable = false,
            unique = true
    )]
    [Email]
    var email: String = ""

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

}
