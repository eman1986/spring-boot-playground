package com.example.webapp.configuration

import com.example.webapp.security.JwtTokenReactFilter
import com.example.webapp.service.JwtService
import com.example.webapp.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(private val jwtService: JwtService, private val userService: UserService) {
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
        }
    }

    @Bean
    fun authenticationWebFilter(
        manager: ReactiveAuthenticationManager,
        jwtConverter: ServerAuthenticationConverter,
    ): AuthenticationWebFilter = AuthenticationWebFilter(manager).apply {
        setRequiresAuthenticationMatcher {
            ServerWebExchangeMatchers.pathMatchers(POST, "/login").matches(it)
        }
        setServerAuthenticationConverter(jwtConverter)
        setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        // allow localhost for dev purposes
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}
