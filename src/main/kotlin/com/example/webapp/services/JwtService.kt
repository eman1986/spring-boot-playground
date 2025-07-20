package com.example.webapp.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.example.webapp.helper.DateTimeHelper
import com.example.webapp.helper.StringHelper
import com.example.webapp.response.AuthToken
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import org.springframework.beans.factory.annotation.Value
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant
import kotlin.uuid.ExperimentalUuidApi

class JwtService {
    @Value($$"${jwt.issuer}")
    private val jwtIssuer: String? = null

    @Value($$"${jwt.audience}")
    private val jwtAudience: String? = null

    @Value($$"${jwt.secret}")
    private val jwtSecret: String? = null

    val jwtVerifier: JWTVerifier = JWT
        .require(Algorithm.HMAC512(jwtSecret))
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .build()

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    fun issueJwt(userId: Int): AuthToken {
        val now = DateTimeHelper.now()
        val exp = now.plus(30, DateTimeUnit.MINUTE, TimeZone.UTC)
        val jti = StringHelper.generateSecureRandomString(15)
        val refreshToken = StringHelper.generateRefreshToken()
        val token = JWT.create()
            .withSubject(userId.toString())
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("uid", userId.toString())
            .withIssuedAt(now.toJavaInstant())
            .withNotBefore(now.toJavaInstant())
            .withExpiresAt(exp.toJavaInstant())
            .withJWTId(jti)
            .sign(Algorithm.HMAC512(jwtSecret))

        return AuthToken(token, refreshToken, jti)
    }

    fun verifyJwt(token: String): Boolean {
        try {
            val algorithm = Algorithm.HMAC512(jwtSecret)
            val verifier = JWT.require(algorithm)
                .withIssuer(jwtIssuer)
                .withAudience(jwtAudience)
                .build()

            verifier.verify(token)
        } catch (_: JWTVerificationException) {
            // Invalid signature/claims
            return false
        }

        return true
    }

    fun getJti(jwt: String): String {
        val token = JWT.decode(jwt)

        return token.id
    }
}
