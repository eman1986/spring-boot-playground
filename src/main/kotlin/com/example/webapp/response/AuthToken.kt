package com.example.webapp.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(val token: String, val refreshToken: String, val jti: String)
