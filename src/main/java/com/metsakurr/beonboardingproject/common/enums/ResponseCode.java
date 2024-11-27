package com.metsakurr.beonboardingproject.common.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("00000", "성공했습니다."),
    NOT_VALID_DATA("40000", "데이터 검증에 실패했습니다."),
    NOT_VALID_TYPE("40001", "파라미터의 타입을 확인해 주세요."),
    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
