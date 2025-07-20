package com.example.webapp.helper

import java.security.SecureRandom
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ExperimentalUuidApi
object StringHelper {
    fun generateLoginCode(): String {
        val loginCode = StringBuilder("")

        while (loginCode.length < 6) {
            val secureRandom = SecureRandom()
            val code = secureRandom.nextInt(9)

            loginCode.append(code.toString())
        }

        return loginCode.toString()
    }

    fun generateRefreshToken(): String {
        return Uuid.random().toString().replace("-", "")
    }

    fun generateSecureRandomString(length: Int): String {
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val random = SecureRandom()
        return (1..length)
            .map { charPool[random.nextInt(charPool.size)] }
            .joinToString("")
    }
}
