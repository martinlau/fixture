package io.fixture.security

import io.fixture.data.model.User
import io.fixture.data.repository.UserRepository
import java.util.HashSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

[Component]
public class UserDetailsManagerImpl [Autowired] (
        val userRepository: UserRepository
): UserDetailsManager {

    [Autowired(required = false)]
    var authenticationManager: AuthenticationManager? = null

    [Transactional]
    public override fun createUser(userDetails: UserDetails) {
        val user = User()

        user.accountNonExpired = userDetails.isAccountNonExpired()
        user.accountNonLocked = userDetails.isAccountNonLocked()
        user.authorities = userDetails.getAuthorities().mapTo(HashSet<String>()) { ga -> ga.getAuthority() }
        user.credentialsNonExpired = userDetails.isCredentialsNonExpired()
        user.enabled = userDetails.isEnabled()
        user.password = userDetails.getPassword()
        user.username = userDetails.getUsername()

        userRepository.save(user)
    }

    [Transactional]
    public override fun updateUser(userDetails: UserDetails) {
        val user = userRepository.findOne(userDetails.getUsername())
        if (user != null) {

            user.accountNonExpired = userDetails.isAccountNonExpired()
            user.accountNonLocked = userDetails.isAccountNonLocked()
            user.authorities = userDetails.getAuthorities().mapTo(HashSet<String>()) { ga -> ga.getAuthority() }
            user.credentialsNonExpired = userDetails.isCredentialsNonExpired()
            user.enabled = userDetails.isEnabled()
            user.password = userDetails.getPassword()
            user.username = userDetails.getUsername()

            userRepository.save(user)
        }
    }

    [Transactional]
    public override fun deleteUser(username: String?) {
        if (username != null)
            userRepository.delete(username)
    }

    [Transactional]
    public override fun changePassword(oldPassword: String?, newPassword: String?) {
        val authentication = SecurityContextHolder.getContext().getAuthentication()

        if (authentication == null)
            throw AccessDeniedException("Can't change password as no Authentication object found in context for current user.")

        val username = authentication.getName()

        if (authenticationManager != null)
            authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(username, oldPassword))

        val user = userRepository.findOne(username!!)
        if (user != null) {
            user.password = newPassword!!

            userRepository.save(user)

            val userDetails = user.toUserDetails()

            val newAuthentication = UsernamePasswordAuthenticationToken(userDetails, newPassword, userDetails.getAuthorities())
            SecurityContextHolder.getContext().setAuthentication(newAuthentication)
        }
    }

    [Transactional(readOnly = true)]
    public override fun userExists(username: String?): Boolean = username != null && userRepository.exists(username)

    [Transactional(readOnly = true)]
    public override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null)
            throw UsernameNotFoundException(username)

        val user = userRepository.findOne(username)
        if (user == null)
            throw UsernameNotFoundException(username)

        return user.toUserDetails()
    }

    fun User.toUserDetails(): UserDetails = SpringUser(
            username,
            password,
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            authorities.map { SimpleGrantedAuthority(it) }
    )

}
