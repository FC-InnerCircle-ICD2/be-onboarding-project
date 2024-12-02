package com.ic.surveyapi.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class ObjectMapperUtil(
    private val objectMapper: ObjectMapper,
) {
    fun <T> convertClass(
        value: Any,
        clazz: Class<T>,
    ): T {
        return objectMapper.convertValue(value, clazz)
    }
}
