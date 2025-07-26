package com.example.webapp.service

import com.example.webapp.helper.StringHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
@Service
class AuthService(private val userService: UserService) {
    suspend fun requestLoginCode(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userService.findUserByEmail(email) ?: return@withContext false
            val code = StringHelper.generateLoginCode()
//        val ulc = UserLoginCode(0, user.id, code)

//        userLoginCodeRepository.create(ulc)
//        mailService.codeRequest(user.email, code)

            return@withContext true
        }
    }
}
