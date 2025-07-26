package com.example.webapp.service

import com.example.webapp.entity.User
import com.example.webapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Service
class UserService(private val userRepository: UserRepository) {

    suspend fun getUserById(userId: Long): User?  = withContext(Dispatchers.IO) {
        userRepository.findById(userId).orElse(null)
    }

    suspend fun findUserByEmail(email: String): User?  = withContext(Dispatchers.IO) {
        userRepository.findOneByEmail(email)
    }

    suspend fun save(user: User): User = withContext(Dispatchers.IO) {
        userRepository.save(user)
    }
}
