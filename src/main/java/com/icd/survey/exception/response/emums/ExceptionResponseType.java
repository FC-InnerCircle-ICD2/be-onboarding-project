package com.icd.survey.exception.response.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionResponseType {
    /* 001 ~ 100 */
    ENTITY_NOT_FNOUND("ER001"),
    ILLEGAL_ARGUMENT("ER003"),

    /* 101 ~ 200 */
    VALIDATION_EXCEPTION("ER101");
    private final String code;
}
