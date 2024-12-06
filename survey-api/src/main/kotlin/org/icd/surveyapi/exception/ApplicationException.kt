package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

abstract class ApplicationException(
    val httpStatus: HttpStatus,
    val errorCode: ErrorCode,
    val errorMessage: String = errorCode.errorMessage
) : RuntimeException(errorMessage)
