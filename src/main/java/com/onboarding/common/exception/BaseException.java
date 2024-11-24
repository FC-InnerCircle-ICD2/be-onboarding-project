package com.onboarding.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private boolean success;
    private String message;

    public BaseException(String message) {
        fillInStackTrace();
        this.success = false;
        this.message = message;
    }
}
