package com.example.webapp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
@Table(name = "users")
@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private val id: Long? = null,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private val createdAt: Instant,

    @UpdateTimestamp
    @Column(name = "updated_at")
    private val updatedAt: Instant,

    @Column(name = "email", unique = true, length = 255, nullable = false)
    private val email: String,

    @Column(name = "first_name", length = 100, nullable = false)
    private val firstName: String? = null,

    @Column(name = "last_name", length = 100, nullable = false)
    private val lastName: String? = null
)
