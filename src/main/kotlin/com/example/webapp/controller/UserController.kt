package com.example.webapp.controller

import com.example.webapp.entity.User
import com.example.webapp.request.user.CreateUserRequest
import com.example.webapp.response.ErrorResponse
import com.example.webapp.response.RestfulResponse
import com.example.webapp.service.UserService
import io.ktor.util.logging.*
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping("/create")
    suspend fun create(@RequestBody request: CreateUserRequest): RestfulResponse<List<String>> {
        try {
            // todo: add validation.
            val user = User(email = request.email, firstName = request.firstName, lastName = request.lastName)

            userService.save(user)

            return RestfulResponse(success = true, data = listOf())
        } catch (e: Exception) {
            log.error(e)

            val err = ErrorResponse(e.message ?: "Error Occurred.")

            return RestfulResponse(success = false, data = listOf(), error = err)
        }
    }
}
