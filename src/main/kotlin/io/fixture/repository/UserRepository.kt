package io.fixture.repository

import io.fixture.domain.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.repository.query.Param
import javax.persistence.QueryHint
import org.springframework.data.jpa.repository.QueryHints

public trait UserRepository : JpaRepository<User, UUID> {

    [Modifying]
    [Query(value = "DELETE FROM User u WHERE u.username = :username")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun delete([Param(value = "username")] username: String)

    [Query(value = "SELECT CASE WHEN (count(u) > 0) THEN true ELSE false END FROM User u WHERE u.username = :username")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun exists([Param(value = "username")] username: String): Boolean

    [Query(value = "SELECT u FROM User u WHERE u.username = :username")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findOne([Param(value = "username")] username: String): User?

}
