package com.example.webapp.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,
    private val firstName: String? = null,
    private val lastName: String? = null
)
