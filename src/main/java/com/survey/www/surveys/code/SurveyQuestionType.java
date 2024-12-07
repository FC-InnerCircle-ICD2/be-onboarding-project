package com.survey.www.surveys.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SurveyQuestionType {
    SHORT_ANSWER("단답형"),
    LONG_ANSWER("장문형"),
    SINGLE_CHOICE("단일 선택 리스트"),
    MULTIPLE_CHOICE("다중 선택 리스트");

    final private String name;
}
