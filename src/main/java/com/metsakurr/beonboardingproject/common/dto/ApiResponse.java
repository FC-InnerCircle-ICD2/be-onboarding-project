package com.metsakurr.beonboardingproject.common.dto;

import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    public ApiResponse(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }
}
