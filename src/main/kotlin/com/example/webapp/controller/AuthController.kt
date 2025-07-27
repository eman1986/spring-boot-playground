package com.example.webapp.controller

import com.example.webapp.request.auth.LoginRequest
import com.example.webapp.response.auth.AuthToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): AuthToken {
        TODO("Login Not Implemented Yet.")
    }
}
