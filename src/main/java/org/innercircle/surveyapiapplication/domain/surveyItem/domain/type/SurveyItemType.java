package org.innercircle.surveyapiapplication.domain.surveyItem.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SurveyItemType {

    SHORT_ANSWER("SHORT_ANSWER"),
    LONG_ANSWER("LONG_ANSWER"),
    SINGLE_CHOICE_ANSWER("SINGLE_CHOICE_ANSWER"),
    MULTI_CHOICE_ANSWER("MULTI_CHOICE_ANSWER");

    private final String type;

}
