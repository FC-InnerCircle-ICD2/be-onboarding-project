package com.ic.surveyapi.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.datetime.LocalDateTime
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()
        // TODO - KotlinModule() deprecated 되었음, deprecated 해결 필요
        .registerModule(KotlinModule())
        .registerModule(SimpleModule().apply {
            addSerializer(LocalDateTime::class.java, KotlinDateTimeCustomConfig.LocalDateTimeSerializer())
            addDeserializer(LocalDateTime::class.java, KotlinDateTimeCustomConfig.LocalDateTimeDeserializer())
        })
}
