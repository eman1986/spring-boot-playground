package com.example.webapp.configuration

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class KotlinSerializationConfig: WebFluxConfigurer {
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        val json = Json {
            prettyPrint = true // Optional: for readable JSON output
            ignoreUnknownKeys = true // Optional: to ignore unknown properties during deserialization
        }

        configurer.defaultCodecs().kotlinSerializationJsonEncoder(KotlinSerializationJsonEncoder(json))
        configurer.defaultCodecs().kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(json))
    }
}
