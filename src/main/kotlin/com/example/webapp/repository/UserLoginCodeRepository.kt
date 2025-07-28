package com.example.webapp.repository

import com.example.webapp.entity.UserLoginCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.NativeQuery
import org.springframework.data.repository.query.Param

interface UserLoginCodeRepository: JpaRepository<UserLoginCode, Long> {
    @NativeQuery("SELECT * FROM user_login_code WHERE code = :code and expires_at >= CURRENT_TIMESTAMP")
    fun getByCode(@Param("code") code: String): UserLoginCode?
}
