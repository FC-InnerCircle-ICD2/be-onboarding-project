package org.innercircle.surveyapiapplication.domain.surveyItem.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SurveyItemType {

    TEXT("TEXT"),
    PARAGRAPH("PARAGRAPH"),
    SINGLE_CHOICE_ANSWER("SINGLE_CHOICE_ANSWER"),
    MULTI_CHOICE_ANSWER("MULTI_CHOICE_ANSWER");

    private final String type;

}
