package io.fixture.data.repository

import io.fixture.data.model.Group
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

public trait GroupRepository: JpaRepository<Group, UUID> {

    [Modifying]
    [Query("DELETE FROM Group g WHERE g.name = :name")]
    fun delete([Param("name")] name: String)

    [Query("SELECT g.name FROM Group g")]
    fun findAllNames(): List<String>

    [Query("SELECT u.username FROM User u INNER JOIN u.groups g WHERE g.name = :name")]
    fun findAllUsernames([Param("name")] name: String): List<String>

    [Query("SELECT g FROM Group g WHERE g.name = :name")]
    fun findOne([Param("name")] name: String): Group?

}
