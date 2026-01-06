package com.example.webapp.controller

import com.example.webapp.entity.User
import com.example.webapp.request.user.CreateUserRequest
import com.example.webapp.request.user.UpdateUserRequest
import com.example.webapp.response.ErrorResponse
import com.example.webapp.response.RestfulResponse
import com.example.webapp.service.CacheService
import com.example.webapp.service.UserService
import io.ktor.util.logging.*
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val cacheService: CacheService, private val userService: UserService) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping("/create")
    suspend fun create(@RequestBody request: CreateUserRequest): RestfulResponse<List<String>> {
        try {
            // todo: add validation.
            val user = User(email = request.email, firstName = request.firstName, lastName = request.lastName)

            userService.save(user)

            return RestfulResponse(true)
        } catch (e: Exception) {
            log.error(e)

            val err = ErrorResponse(e.message ?: "Error Occurred.")

            return RestfulResponse(success = false, error = err)
        }
    }

    @GetMapping("/current")
    suspend fun authUser(): RestfulResponse<User> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = (authentication.principal as UserDetails).username.toLong()
        val user = cacheService.getUserById(userId).awaitSingle() ?: return RestfulResponse(false)

        return RestfulResponse(true, user)
    }

    @PutMapping("/update")
    suspend fun updateUser(@RequestBody request: UpdateUserRequest): RestfulResponse<Boolean> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = (authentication.principal as UserDetails).username.toLong()

        // TODO
//        userService.updateUser(userId, request)

        return RestfulResponse(true)
    }

    @DeleteMapping("/delete")
    suspend fun delete(): RestfulResponse<Boolean> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = (authentication.principal as UserDetails).username.toLong()

        userService.delete(userId)

        return RestfulResponse(true)
    }
}
