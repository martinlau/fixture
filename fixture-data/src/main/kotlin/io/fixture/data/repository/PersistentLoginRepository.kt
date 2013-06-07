package io.fixture.data.repository

import io.fixture.data.model.PersistentLogin
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

public trait PersistentLoginRepository: JpaRepository<PersistentLogin, UUID> {

    [Modifying]
    [Query("DELETE FROM PersistentLogin pl WHERE pl.user = (SELECT u FROM User u WHERE u.username = :username)")]
    fun deleteAllForUsername([Param("username")] username: String)

    [Query("SELECT pl FROM PersistentLogin pl WHERE pl.series = :series")]
    fun findOne([Param("series")] series: String): PersistentLogin?

}
