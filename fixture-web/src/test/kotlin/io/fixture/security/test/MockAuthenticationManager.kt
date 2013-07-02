package io.fixture.security.test

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

class MockAuthenticationManager: AuthenticationManager {

    public var callCount: Int = 0

    override public fun authenticate(authentication: Authentication): Authentication {
        callCount++
        return authentication
    }

}
