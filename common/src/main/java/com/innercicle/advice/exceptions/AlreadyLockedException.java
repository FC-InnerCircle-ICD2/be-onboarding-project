package com.innercicle.advice.exceptions;

public class AlreadyLockedException extends RuntimeException {

    public AlreadyLockedException() {
        super("Already locked");
    }

    public AlreadyLockedException(String message) {
        super(message);
    }

}
