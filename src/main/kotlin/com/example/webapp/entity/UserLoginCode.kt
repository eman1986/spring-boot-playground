package com.example.webapp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Table(name = "user_login_code", indexes = [
    Index(name = "idx_userId_code", columnList = "userId, code")
])
@Entity
class UserLoginCode(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    var id: Long? = null,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    var createdAt: Instant = LocalDateTime.now().toInstant(ZoneOffset.UTC),

    @Column(updatable = false, name = "expires_at")
    var expiresAt: Instant? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Column(name = "code", unique = true, length = 6, nullable = false)
    var code: String
)
