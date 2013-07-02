package io.fixture.repository

import io.fixture.domain.Group
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.persistence.QueryHint
import org.springframework.data.jpa.repository.QueryHints

public trait GroupRepository: JpaRepository<Group, UUID> {

    [Modifying]
    [Query("DELETE FROM Group g WHERE g.name = :name")]
    [QueryHints(array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun delete([Param("name")] name: String)

    [Query("SELECT g.name FROM Group g")]
    [QueryHints(array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findAllNames(): List<String>

    [Query("SELECT u.username FROM User u INNER JOIN u.groups g WHERE g.name = :name")]
    [QueryHints(array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findAllUsernames([Param("name")] name: String): List<String>

    [Query("SELECT g FROM Group g WHERE g.name = :name")]
    [QueryHints(array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findOne([Param("name")] name: String): Group?

}
