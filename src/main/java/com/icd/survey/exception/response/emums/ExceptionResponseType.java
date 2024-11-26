package com.icd.survey.exception.response.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionResponseType {
    /* 001 ~ 100 */
    ENTITY_NOT_FNOUND("ER001", "존재하지 않는 데이터 입니다."),
    ALREADY_EXISTS("ER002", "이미 존재하는 데이터 입니다."),
    ILLEGAL_ARGUMENT("ER003", "입력값에 문제가 있습니다."),

    /* 101 ~ 200 */
    VALIDATION_EXCEPTION("ER101", "");
    private final String code;
    private final String message;
}
