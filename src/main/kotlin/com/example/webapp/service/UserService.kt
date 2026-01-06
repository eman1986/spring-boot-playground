package com.example.webapp.service

import com.example.webapp.entity.User
import com.example.webapp.entity.UserLoginCode
import com.example.webapp.repository.UserLoginCodeRepository
import com.example.webapp.repository.UserRepository
import io.lettuce.core.KillArgs.Builder.user
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userLoginCodeRepository: UserLoginCodeRepository,
    loginCodeRepository: UserLoginCodeRepository
) {
    suspend fun getUserById(userId: Long): User?  = withContext(Dispatchers.IO) {
        userRepository.findById(userId).getOrNull()
    }

    suspend fun getByCode(code: String): UserLoginCode? = withContext(Dispatchers.IO) {
        userLoginCodeRepository.getByCode(code)
    }

    suspend fun findUserByEmail(email: String): User?  = withContext(Dispatchers.IO) {
        userRepository.findOneByEmail(email)
    }

    suspend fun save(user: User): User = withContext(Dispatchers.IO) {
        userRepository.save(user)
    }

    suspend fun saveLogin(userLogin: UserLoginCode): UserLoginCode = withContext(Dispatchers.IO) {
        userLoginCodeRepository.save(userLogin)
    }

    suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        userRepository.deleteById(id)
    }

    suspend fun deleteLogin(loginId: Long): Unit = withContext(Dispatchers.IO) {
        userLoginCodeRepository.deleteById(loginId)
    }
}
