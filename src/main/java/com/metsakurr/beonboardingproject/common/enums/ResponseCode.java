package com.metsakurr.beonboardingproject.common.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("00000", "성공했습니다.");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
