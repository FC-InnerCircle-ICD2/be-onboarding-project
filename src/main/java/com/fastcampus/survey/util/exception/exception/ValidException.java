package com.fastcampus.survey.util.exception.exception;

import org.springframework.http.HttpStatus;

public class ValidException extends RuntimeException{
    private final HttpStatus status;

    public ValidException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
