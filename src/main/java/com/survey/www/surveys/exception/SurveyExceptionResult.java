package com.survey.www.surveys.exception;

import com.survey.www.commons.config.exception.CommonExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SurveyExceptionResult implements CommonExceptionResult {
    NOT_FOUND_SURVEY("등록된 설문조사를 찾을 수 없습니다"),
    NOT_FOUND_SURVEY_QUESTION("등록된 설문조사를 찾을 수 없습니다"),
    NOT_EXIST_SURVEY("이미 삭제된 설문조사 입니다."),
    NOT_EXIST_SURVEY_QUESTION("이미 삭제된 설문조사 항목 입니다."),
    NOT_EXIST_SURVEY_QUESTION_OPTION("이미 삭제된 설문조사 항목 리스트 입니다."),
    INVALID_COUNT_SURVEY_QUESTIONS("설문 받을 항목은 1개 ~ 10개까지 포함할 수 있습니다"),
    UNAUTHORIZED_SURVEY_MODIFICATION("설문조사를 수정할 권한이 없습니다.");

    private final String message;
}
