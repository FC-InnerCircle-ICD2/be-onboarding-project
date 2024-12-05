package com.ic.surveyapi.exception.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import survey.exception.CustomErrorException

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(exception: Exception): ResponseEntity<CommonErrorResponse> {
        logger.error("Exception exception : {}", exception.message, exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            CommonErrorResponse.of(
                exception = exception,
            )
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(exception: RuntimeException): ResponseEntity<CommonErrorResponse> {
        logger.error("Runtime exception : {}", exception.message, exception)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            CommonErrorResponse.of(
                exception = exception,
            )
        )
    }

    @ExceptionHandler(CustomErrorException::class)
    fun customExceptionHandler(exception: CustomErrorException): ResponseEntity<CommonErrorResponse> {
        logger.error("CustomErrorException exception : {}", exception.message, exception)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                CommonErrorResponse.of(
                errorCode = exception
            ))
    }
}
