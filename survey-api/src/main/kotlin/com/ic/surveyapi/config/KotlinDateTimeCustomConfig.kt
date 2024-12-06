package com.ic.surveyapi.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

class KotlinDateTimeCustomConfig {
    class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
        private val formatter = DateTimeFormatter.ofPattern(DATE_TINE_PATTER)

        override fun serialize(
            value: LocalDateTime,
            generator: JsonGenerator,
            serializers: SerializerProvider,
        ) {
            generator.writeString(value.toJavaLocalDateTime().format(formatter))
        }
    }

    class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
        override fun deserialize(
            parser: com.fasterxml.jackson.core.JsonParser,
            context: com.fasterxml.jackson.databind.DeserializationContext,
        ): LocalDateTime {
            return LocalDateTime.parse(parser.text)
        }
    }

    companion object {
        private const val DATE_TINE_PATTER = "yyyy-MM-dd'T'HH:mm:ss"
    }
}
