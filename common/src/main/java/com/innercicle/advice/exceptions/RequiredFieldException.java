package com.innercicle.advice.exceptions;

public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(String message) {
        super(message);
    }

}
