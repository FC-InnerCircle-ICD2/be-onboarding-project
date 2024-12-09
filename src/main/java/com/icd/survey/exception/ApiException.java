package com.icd.survey.exception;

import com.icd.survey.exception.response.emums.ExceptionResponseType;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ExceptionResponseType type;

    public ApiException(ExceptionResponseType type) {
        super(type.getMessage());
        this.type = type;
    }

    public ApiException(ExceptionResponseType type, String message) {
        super(message);
        this.type = type;
    }
}
