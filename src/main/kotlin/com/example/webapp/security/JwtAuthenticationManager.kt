package com.example.webapp.security

import com.example.webapp.service.JwtService
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationManager(private val jwtService: JwtService): ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()

        return Mono.fromCallable { jwtService.verifyJwt(authToken) }
            .onErrorResume { Mono.empty() }
            .handle { isValid: Boolean, sink ->
                if (isValid) {
                    val username = jwtService.getUid(authToken)
                    val roles = mutableListOf(SimpleGrantedAuthority("ROLE_USER"))

                    sink.next(UsernamePasswordAuthenticationToken(username, null, roles))
                } else {
                    sink.error(BadCredentialsException("Invalid JWT token"))
                }
            }
    }
}
