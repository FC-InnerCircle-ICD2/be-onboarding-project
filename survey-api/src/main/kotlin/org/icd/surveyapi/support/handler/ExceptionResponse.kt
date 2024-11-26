package org.icd.surveyapi.support.handler

import org.icd.surveyapi.exception.ErrorCode
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

class ExceptionResponse private constructor(
    val exceptionDateTime: String,
    val httpStatus: HttpStatus,
    val errorCode: ErrorCode,
    val errorMessage: String?
) {
    companion object {
        fun of(
            httpStatus: HttpStatus,
            errorCode: ErrorCode,
            errorMessage: String? = errorCode.errorMessage
        ): ExceptionResponse {
            return ExceptionResponse(
                exceptionDateTime = OffsetDateTime.now().toString(),
                httpStatus = httpStatus,
                errorCode = errorCode,
                errorMessage = errorMessage
            )
        }
    }
}