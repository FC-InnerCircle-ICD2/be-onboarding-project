package org.icd.surveyapi.support.utils

import org.icd.surveyapi.exception.MissingRequiredFieldException

inline fun <reified T> T?.extract(fieldName: String): T {
    if (this == null || (this is String && this.isBlank()) || (this is Collection<*> && this.isEmpty())) {
        throw MissingRequiredFieldException(fieldName)
    }
    return this
}