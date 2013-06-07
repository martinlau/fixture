package io.fixture.data.repository

import io.fixture.data.model.UserProfile
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

public trait UserProfileRepository: JpaRepository<UserProfile, UUID>
