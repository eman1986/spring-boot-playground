package com.example.webapp.service

import com.example.webapp.entity.User
import com.example.webapp.repository.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CacheService(private val userRepository: UserRepository) {

    @Cacheable("user")
    public fun getUserById(userId: Long): User {
        return User()
    }

}
