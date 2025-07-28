package com.example.webapp.service

import com.example.webapp.entity.UserLoginCode
import com.example.webapp.exceptions.AuthenticationException
import com.example.webapp.helper.StringHelper
import com.example.webapp.response.auth.AuthToken
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Service
class AuthService(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val mailService: MailService
) {
    suspend fun requestLoginCode(email: String): Boolean {
        val user = userService.findUserByEmail(email) ?: return false
        val code = StringHelper.generateLoginCode()
        val expiresAt = LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.UTC)
        val ulc = UserLoginCode(userId = user.id!!, code = code, expiresAt = expiresAt)

        userService.saveLogin(ulc)
        mailService.codeRequest(user.email, code)

        return true
    }

    suspend fun authenticate(code: String): AuthToken {
        val codeRequest = userService.getByCode(code) ?: throw AuthenticationException("Invalid Code")

        // delete used login code.
        userService.deleteLogin(codeRequest.id!!)

        return jwtService.issueJwt(codeRequest.userId)
    }
}
