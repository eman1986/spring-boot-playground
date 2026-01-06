package com.example.webapp.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val email: String,
    val firstName: String,
    val lastName: String?,
)
