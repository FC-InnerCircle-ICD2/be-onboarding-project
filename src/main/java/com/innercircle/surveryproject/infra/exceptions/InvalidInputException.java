package com.innercircle.surveryproject.infra.exceptions;

/**
 * 사용자에 의한 입력 값 예외 처리
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

}
