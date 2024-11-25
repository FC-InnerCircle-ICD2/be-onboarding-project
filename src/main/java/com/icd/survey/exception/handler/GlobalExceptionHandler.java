package com.icd.survey.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* IllegalArgumentException Handler*/
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ExceptionResponse> handlerIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException : {}", e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(e.getMessage()));
    }

    /* Validation Handler*/
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : {}", e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(createValidationMessage(e.getBindingResult())));
    }

    private static String createValidationMessage(BindingResult result) {
        StringBuilder sb = new StringBuilder();

        Boolean isFirst = Boolean.TRUE;

        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(error.getField());
            sb.append("] ");
            sb.append(error.getDefaultMessage());
        }

        return sb.toString();
    }


}
