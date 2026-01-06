package com.example.webapp.service

import com.example.webapp.entity.User
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class CacheService(private val userService: UserService) {

    @Cacheable("user", key = "#userId")
    suspend fun getUserById(userId: Long): Mono<User?> {
        return Mono.justOrEmpty(userService.getUserById(userId)).cache(Duration.ofMinutes(60))
    }

}
