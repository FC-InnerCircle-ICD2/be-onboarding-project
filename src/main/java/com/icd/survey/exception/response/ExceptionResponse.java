package com.icd.survey.exception.response;

import com.icd.survey.exception.response.emums.ExceptionResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private String message;
    private String code;

    public ExceptionResponse(String message, ExceptionResponseType type) {
        this.message = message;
        this.code = type.getCode();
    }

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public ExceptionResponse(ExceptionResponseType type) {
        this.code = type.getCode();
    }

}
