package com.icd.survey.exception.response.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionResponseType {
    /* 001 ~ 99 */
    ENTITY_NOT_FNOUND("ER001", "존재하지 않는 데이터 입니다."),
    ALREADY_EXISTS("ER002", "이미 존재하는 데이터 입니다."),
    ILLEGAL_ARGUMENT("ER003", "입력값에 문제가 있습니다."),

    /* 100 ~ 199 */
    VALIDATION_EXCEPTION("ER100", ""),

    /* 500 ~ 599 */
    INTERVAL_SERVER_EXCEPTION("ER500","서버 에러가 발생했습니다.");
    private final String code;
    private final String message;
}
