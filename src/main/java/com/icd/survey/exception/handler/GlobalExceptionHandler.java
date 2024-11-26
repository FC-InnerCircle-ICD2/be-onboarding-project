package com.icd.survey.exception.handler;

import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.ExceptionResponse;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomApiException(ApiException e) {
        logError(e);
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponse
                        .builder()
                        .code(e.getType().getCode())
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        logError(e);
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponse
                        .builder()
                        .message(e.getMessage())
                        .code(ExceptionResponseType.ENTITY_NOT_FNOUND.getCode())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ExceptionResponse> handlerIllegalArgumentException(IllegalArgumentException e) {
        logError(e);
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponse
                        .builder()
                        .message(e.getMessage())
                        .code(ExceptionResponseType.ILLEGAL_ARGUMENT.getCode())
                        .build());
    }

    /* Validation Handler*/
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logError(e);
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponse
                        .builder()
                        .message(createValidationMessage(e.getBindingResult()))
                        .code(ExceptionResponseType.VALIDATION_EXCEPTION.getCode())
                        .build()
                );
    }

    private void logError(Exception e) {
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
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
