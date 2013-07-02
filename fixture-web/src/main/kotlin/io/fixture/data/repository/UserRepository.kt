package io.fixture.data.repository

import io.fixture.data.model.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.repository.query.Param
import javax.persistence.QueryHint
import org.springframework.data.jpa.repository.QueryHints

public trait UserRepository : JpaRepository<User, UUID> {

    [Modifying]
    [Query("DELETE FROM User u WHERE u.username = :username")]
    [QueryHints(array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun delete([Param("username")] username: String)

    [Query("SELECT CASE WHEN (count(u) > 0) THEN true ELSE false END FROM User u WHERE u.username = :username")]
    [QueryHints(array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun exists([Param("username")] username: String): Boolean

    [Query("SELECT u FROM User u WHERE u.username = :username")]
    [QueryHints(array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findOne([Param("username")] username: String): User?

}
