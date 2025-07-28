package com.example.webapp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Table(name = "users", indexes = [
    Index(name = "idx_name_email", columnList = "first_name, last_name, email")
])
@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    val id: Long? = null,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    val createdAt: Instant = LocalDateTime.now().toInstant(ZoneOffset.UTC),

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant = LocalDateTime.now().toInstant(ZoneOffset.UTC),

    @Column(name = "email", unique = true, length = 255, nullable = false)
    val email: String,

    @Column(name = "first_name", length = 100, nullable = false)
    val firstName: String? = null,

    @Column(name = "last_name", length = 100, nullable = false)
    val lastName: String? = null
)
