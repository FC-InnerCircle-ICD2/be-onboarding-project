package com.onboarding.beonboardingproject.common.global;

import lombok.Getter;

@Getter
public class ApiResult<T> {
    private final String code;
    private final String message;
    private T data;

    public ApiResult(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public static ApiResult success(String message) {
        return new ApiResult(ResponseCode.SUCCESS, message);
    }

    public static ApiResult failure(String errorMessage) {
        return new ApiResult(ResponseCode.FAILURE, errorMessage);
    }
}
