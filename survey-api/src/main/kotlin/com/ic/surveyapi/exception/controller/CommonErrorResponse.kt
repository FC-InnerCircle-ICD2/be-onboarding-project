package com.ic.surveyapi.exception.controller

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import survey.exception.CustomErrorException

class CommonErrorResponse(
    val errorCode: Int,
    val message: String?,
    val currentDateTime: LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
) {
    companion object {
        fun of(exception: Exception): CommonErrorResponse {
            return CommonErrorResponse(
                errorCode = exception.cause.hashCode(),
                message = exception.message,
            )
        }

        fun of(errorCode: CustomErrorException): CommonErrorResponse {
            return CommonErrorResponse(
                errorCode = errorCode.errorCode.code,
                message = errorCode.message
            )
        }
    }
}
