package com.example.webapp.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration


@Configuration
class RedisConfig {
    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration {
        val config = RedisCacheConfiguration.defaultCacheConfig()

        config.disableCachingNullValues()

        return config
    }
}
