package com.example.webapp.service

import com.example.webapp.entity.User
import com.example.webapp.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserById(userId: Int): User? {
        return userRepository.findById(1).orElse(null)
    }

    fun findUser(): User? {
        return userRepository.findById(1).orElse(null)
    }
}
