package com.icd.survey.api.enums.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EssentialType {
    ESSENTIAL(1, "essential"),
    OPTIONAL(0, "optional");
    private final Integer essential;
    private final String essentialString;
}
