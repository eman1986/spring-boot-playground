package com.example.webapp.service

import com.example.webapp.entity.User
import com.example.webapp.repository.UserRepository

class UserService(private val userRepository: UserRepository) {

    fun findUser(): User {
        return User()
    }
}
