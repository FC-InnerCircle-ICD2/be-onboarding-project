package com.icd.survey.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ResponseType {
    SHORT_ANSWER(1, "short answer", 100),
    LONG_ANSWER(2, "long answer", 1000),
    SINGLE_CHOICE(3, "single choice", 1),
    MULTI_CHOICE(4, "multi choice", 1);
    private final Integer type;
    private final String typeString;
    private final Integer count;

}
