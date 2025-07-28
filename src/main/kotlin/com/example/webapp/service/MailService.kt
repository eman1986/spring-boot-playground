package com.example.webapp.service

import com.example.webapp.response.mailgun.MailgunMessageResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.pebbletemplates.pebble.PebbleEngine
import io.pebbletemplates.pebble.template.PebbleTemplate
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.StringWriter

@Service
class MailService {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Value($$"${mailgun.apikey}")
    private val mailgunApiKey: String = ""

    @Value($$"${mailgun.domain}")
    private val mailgunDomain: String = ""

    @Value($$"${email.fromName}")
    private val fromName: String = ""

    @Value($$"${email.fromEmail}")
    private val fromEmail: String = ""

    /**
     * Send user their login code.
     *
     * @param email recipient's email.
     * @param code login code.
     */
    suspend fun codeRequest(email: String, code: String) {
        val writer = StringWriter()

        loadTemplateFile("codeRequest").evaluate(writer, mapOf("code" to code))
        send(email, "Login Code", writer.toString())
    }

    private fun getClient(): HttpClient = HttpClient(Java) {
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(username = "api", password = mailgunApiKey)
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(DefaultRequest) {
            url("https://api.mailgun.net/v3/")
        }
    }

    private suspend fun send(email: String, subject: String, emailContent: String) {
        val resp = getClient().submitForm(
            url = "$mailgunDomain/messages",
            formParameters = parameters {
                append("from", "$fromName <$fromEmail>")
                append("to", email)
                append("subject", subject)
                append("html", emailContent)
            }
        )

        // report failure.
        if (!resp.status.isSuccess()) {
            val respBody: MailgunMessageResponse = resp.body()

            log.error(respBody.message)

            throw Exception("Failed to send out email.")
        }
    }

    private fun loadTemplateFile(tpl: String): PebbleTemplate = PebbleEngine
        .Builder()
        .build().
        getTemplate("templates/${tpl}.peb")
}
