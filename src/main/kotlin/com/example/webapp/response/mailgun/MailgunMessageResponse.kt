package com.example.webapp.response.mailgun

import kotlinx.serialization.Serializable

@Serializable
data class MailgunMessageResponse(val id: String?, val message: String)
