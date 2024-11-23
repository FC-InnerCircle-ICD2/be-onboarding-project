package com.onboarding.core.global.handler;

import com.onboarding.core.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<String> handleCustomException(CustomException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message);
  }

}
