package com.example.webapp.configuration

import com.example.webapp.security.JwtAuthenticationConverter
import com.example.webapp.security.JwtAuthenticationManager
import com.example.webapp.security.JwtTokenReactFilter
import com.example.webapp.service.JwtService
import com.example.webapp.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(private val jwtService: JwtService, private val userService: UserService) {
    @Value($$"${jwt.secret}")
    private val jwtSecret: String = ""

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            // Define public and private routes
            authorizeExchange {
                authorize("/", permitAll)
                authorize("/login", permitAll)
                authorize("/verify-code", permitAll)
                authorize("/refresh", permitAll)
                authorize("/register", permitAll)
                authorize("/json", permitAll)
                authorize("/**", authenticated)
            }
            addFilterAt(JwtTokenReactFilter(jwtService), SecurityWebFiltersOrder.AUTHORIZATION)
            csrf { disable() }
            formLogin { disable() }
            logout { disable() }
            cors {}
            oauth2ResourceServer {
                jwt { }
            }
            headers {
                frameOptions {
                    mode = XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN
                }
            }
        }
    }

    @Bean
    fun authenticationWebFilter(): AuthenticationWebFilter = AuthenticationWebFilter(JwtAuthenticationManager(jwtService)).apply {
        setRequiresAuthenticationMatcher {
            ServerWebExchangeMatchers.pathMatchers(POST, "/login").matches(it)
        }
        setServerAuthenticationConverter(JwtAuthenticationConverter(jwtService, userService))
        setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
    }

    @Bean
    fun jwtDecoder(): ReactiveJwtDecoder {
        return NimbusReactiveJwtDecoder
            .withSecretKey(SecretKeySpec(jwtSecret.toByteArray(), "HS512"))
            .build()
    }
}
