package com.metsakurr.beonboardingproject.common.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("00000", "성공했습니다."),
    NOT_VALID_DATA("40000", "데이터 검증에 실패했습니다."),
    NOT_VALID_TYPE("40001", "파라미터의 타입을 확인해 주세요."),

    NOT_FOUND_SURVEY("40010", "유효하지 않은 설문조사입니다. [설문조사 이름], [설문조사 설명]을 확인해 주세요")
    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
