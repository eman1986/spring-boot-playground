package com.example.webapp.security

import com.example.webapp.service.JwtService
import com.example.webapp.service.UserService
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.function.Function


@Component
class JwtAuthenticationConverter(private val jwtService: JwtService, private val userService: UserService): ServerAuthenticationConverter {
    companion object {
        private const val BEARER = "Bearer "
    }

    override fun convert(exchange: ServerWebExchange): Mono<Authentication?> {
        return Mono.justOrEmpty(exchange)
            .flatMap { Mono.justOrEmpty(it.request.headers.getFirst(HttpHeaders.AUTHORIZATION)) }
            .filter { it.startsWith(BEARER) }
            .map { it.substring(BEARER.length)}
            .map<Authentication?>(Function { token: String? -> getAuthentication(token!!) })
    }

    private fun getAuthentication(token: String): Authentication? {
        runBlocking {
            val userId = jwtService.getUid(token)
            val user = userService.getUserById(userId) ?: throw InvalidBearerTokenException("Invalid token")

            return@runBlocking UsernamePasswordAuthenticationToken(user, "", listOf(SimpleGrantedAuthority("USER")))
        }

        return null
    }
}
