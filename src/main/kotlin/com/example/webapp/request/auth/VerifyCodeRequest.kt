package com.example.webapp.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeRequest(val code: String)
