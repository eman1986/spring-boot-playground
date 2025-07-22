package com.example.webapp.response

import kotlinx.serialization.Serializable

@Serializable
data class RestfulResponse<T>(val success: Boolean, val data: T? = null, val error: ErrorResponse? = null)
