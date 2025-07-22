package com.example.webapp.service

import com.example.webapp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CacheService(private val userRepository: UserRepository) {

//    @OptIn(ExperimentalTime::class)
//    @Cacheable("user")
//    public fun getUserById(userId: Long): User {
//        return User()
//    }

}
