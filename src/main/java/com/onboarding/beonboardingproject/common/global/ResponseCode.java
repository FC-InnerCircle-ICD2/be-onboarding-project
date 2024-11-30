package com.onboarding.beonboardingproject.common.global;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("0", "성공"),
    FAILURE("1", "실패" );

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
