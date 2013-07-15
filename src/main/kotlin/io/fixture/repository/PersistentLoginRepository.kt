package io.fixture.repository

import io.fixture.domain.PersistentLogin
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.persistence.QueryHint
import org.springframework.data.jpa.repository.QueryHints

public trait PersistentLoginRepository: JpaRepository<PersistentLogin, UUID> {

    [Modifying]
    [Query(value = "DELETE FROM PersistentLogin pl WHERE pl.user = (SELECT u FROM User u WHERE u.username = :username)")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun deleteAllForUsername([Param(value = "username")] username: String)

    [Query(value = "SELECT pl FROM PersistentLogin pl WHERE pl.series = :series")]
    [QueryHints(value = array(
            QueryHint(name = "org.hibernate.cacheable", value = "true")
    ))]
    fun findOne([Param(value = "series")] series: String): PersistentLogin?

}
