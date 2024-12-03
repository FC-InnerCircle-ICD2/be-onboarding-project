package com.metsakurr.beonboardingproject.common.exception;

import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final ResponseCode responseCode;

    public ServiceException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
