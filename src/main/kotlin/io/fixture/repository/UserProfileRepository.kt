package io.fixture.repository

import io.fixture.domain.UserProfile
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

public trait UserProfileRepository: JpaRepository<UserProfile, UUID>