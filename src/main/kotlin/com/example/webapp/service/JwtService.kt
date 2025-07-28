package com.example.webapp.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.example.webapp.helper.StringHelper
import com.example.webapp.response.auth.AuthToken
import io.ktor.util.logging.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.uuid.ExperimentalUuidApi

@Service
class JwtService {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Value($$"${jwt.issuer}")
    private val jwtIssuer: String? = null

    @Value($$"${jwt.audience}")
    private val jwtAudience: String? = null

    @Value($$"${jwt.secret}")
    private val jwtSecret: String? = null

    @OptIn(ExperimentalUuidApi::class)
    fun issueJwt(userId: Long): AuthToken {
        val now = LocalDateTime.now()
        val exp = now.plusMinutes(30)
        val jti = StringHelper.generateSecureRandomString(15)
        val refreshToken = StringHelper.generateRefreshToken()
        val token = JWT.create()
            .withSubject(userId.toString())
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withIssuedAt(now.toInstant(ZoneOffset.UTC))
            .withNotBefore(now.toInstant(ZoneOffset.UTC))
            .withExpiresAt(exp.toInstant(ZoneOffset.UTC))
            .withJWTId(jti)
            .sign(Algorithm.HMAC512(jwtSecret))

        return AuthToken(token, refreshToken, jti)
    }

    fun verifyJwt(token: String): Boolean {
        try {
            val verifier = JWT.require(Algorithm.HMAC512(jwtSecret))
                .withIssuer(jwtIssuer)
                .withAudience(jwtAudience)
                .build()

            verifier.verify(token)
        } catch (e: JWTVerificationException) {
            log.error(e)

            // Invalid signature/claims
            return false
        }

        return true
    }

    fun getJti(jwt: String): String {
        val token = JWT.decode(jwt)

        return token.id
    }

    fun getUid(jwt: String): Long {
        val token = JWT.decode(jwt)

        return token.subject.toLong()
    }
}
