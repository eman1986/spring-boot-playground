package com.example.webapp.controller

import com.example.webapp.request.auth.LoginRequest
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    public fun login(@RequestBody request: LoginRequest) {
        TODO("Login Not Implemented Yet.")
    }
}
