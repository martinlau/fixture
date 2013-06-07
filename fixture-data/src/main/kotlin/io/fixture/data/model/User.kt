package io.fixture.data.model

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

[Entity]
[Table(name = "users")]
public class User : BasePersistable() {

    enum class TokenType {
        ACTIVATION
        PASSWORD_RESET
    }

    [Column(
            name = "account_non_expired",
            nullable = false
    )]
    var accountNonExpired: Boolean = false

    [Column(
            name = "account_non_locked",
            nullable = false
    )]
    var accountNonLocked: Boolean = false

    [CollectionTable(
            name = "user_account_tokens",
            joinColumns = array(JoinColumn(name = "user_uuid"))
    )]
    [Column(name = "token")]
    [ElementCollection(targetClass = javaClass<UUID>())]
    [MapKeyClass(javaClass<User.TokenType>())]
    [MapKeyColumn(
            name = "token_type",
            unique = true
    )]
    [MapKeyEnumerated(EnumType.STRING)]
    [Type(`type` = "uuid-char")]
    var accountTokens: MutableMap<User.TokenType, UUID> = HashMap()

    [CollectionTable(
            name = "user_authorities",
            joinColumns = array(JoinColumn(name = "user_uuid"))
    )]
    [Column(
            name = "authority",
            nullable = false
    )]
    [ElementCollection(targetClass = javaClass<String>())]
    var authorities: MutableSet<String> = HashSet()

    [Column(
            name = "credentials_non_expired",
            nullable = false
    )]
    var credentialsNonExpired: Boolean = false

    [Column(
            name = "enabled",
            nullable = false
    )]
    var enabled: Boolean = false

    [ManyToMany(
            mappedBy = "users",
            targetEntity = javaClass<Group>()
    )]
    var groups: MutableSet<Group> = HashSet()

    [Column(
            name = "password",
            nullable = false
    )]
    var password: String = ""

    [OneToMany(
            mappedBy = "user",
            targetEntity = javaClass<PersistentLogin>()
    )]
    [MapKeyClass(javaClass<String>())]
    [MapKeyColumn(name = "series")]
    var persistentLogins: MutableMap<String, PersistentLogin> = HashMap()

    [OneToOne(
            cascade = array(CascadeType.ALL),
            mappedBy = "user",
            optional = false,
            targetEntity = javaClass<UserProfile>()
    )]
    var profile: UserProfile? = null

    [Column(
            name = "username",
            nullable = false,
            unique = true
    )]
    var username: String = ""

}
