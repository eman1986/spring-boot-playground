package com.example.webapp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Table(name = "user_login_code", indexes = [
    Index(name = "idx_name_email", columnList = "name, email"),
    Index(name = "idx_unique_code", columnList = "code",  unique = true)
])
@Entity
class UserLoginCode(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private val id: Long? = null,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private val createdAt: Instant = Clock.System.now(),

    @UpdateTimestamp
    @Column(name = "expires_at")
    private val expiresAt: Instant,

    @Column(name = "user_id", nullable = false)
    val userId: Int,

    @Column(name = "code", unique = true, length = 6, nullable = false)
    val code: String
)
