package org.icd.surveyapi.support.handler

import jakarta.servlet.http.HttpServletRequest
import org.icd.surveyapi.exception.ApplicationException
import org.icd.surveyapi.exception.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(
    private val request: HttpServletRequest
) {
    private val log = LoggerFactory.getLogger(javaClass)
    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(exception: Exception): ResponseEntity<ExceptionResponse> {
        val traceId = request.getAttribute("traceId") ?: "no-traceId"
        val response = getExceptionResponse(exception)

        log.error(
            "[{}] - [{}] {} - errorMessage: {}",
            traceId,
            response.httpStatus,
            exception.javaClass.simpleName,
            response.errorMessage,
            exception
        )

        return ResponseEntity.status(response.httpStatus.value())
            .body(response)
    }

    private fun getExceptionResponse(exception: Exception): ExceptionResponse {
        val httpStatus: HttpStatus
        val errorCode: ErrorCode
        val errorMessage: String?

        when (exception) {
            is ApplicationException -> {
                httpStatus = exception.httpStatus
                errorCode = exception.errorCode
                errorMessage = exception.errorMessage
            }

            else -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                errorCode = ErrorCode.INTERNAL_SERVER_ERROR
                errorMessage = exception.message
            }
        }

        return ExceptionResponse.of(httpStatus, errorCode, errorMessage)
    }

}