package com.example.webapp.controller

import com.example.webapp.exceptions.AuthenticationException
import com.example.webapp.request.auth.LoginRequest
import com.example.webapp.request.auth.VerifyCodeRequest
import com.example.webapp.response.RestfulResponse
import com.example.webapp.response.auth.AuthToken
import com.example.webapp.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authService: AuthService) {
    @PostMapping("/login")
    suspend fun login(@RequestBody request: LoginRequest): RestfulResponse<List<String>> {
        val requestSuccessful = authService.requestLoginCode(request.email)

        if (!requestSuccessful) {
            throw AuthenticationException("Login Failed.")
        }

        return RestfulResponse(true)
    }

    @PostMapping("/login/verify")
    suspend fun verifyCode(@RequestBody request: VerifyCodeRequest): RestfulResponse<AuthToken> {
        // todo: add validation.
        val authToken = authService.authenticate(request.code)

        return RestfulResponse(true, authToken)
    }
}
