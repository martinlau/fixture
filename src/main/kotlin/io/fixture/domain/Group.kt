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
public class Group: BasePersistable() {

    [Column(
            name = "authority",
            nullable = false
    )]
    [CollectionTable(
            name = "group_authorities",
            joinColumns = array(JoinColumn(name = "group_uuid"))
    )]
    [ElementCollection(targetClass = javaClass<String>())]
    var authorities: MutableSet<String> = HashSet()

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
    var name: String = ""

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

}
