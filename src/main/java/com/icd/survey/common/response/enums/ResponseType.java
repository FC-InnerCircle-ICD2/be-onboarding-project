package com.icd.survey.common.response.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseType {
    SUCCESS("SUCCESS"),
    ;
    private final String responseMessage;
}
