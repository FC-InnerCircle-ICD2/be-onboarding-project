package com.survey.www.surveys.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyException extends RuntimeException {
    private final SurveyExceptionResult surveyExceptionResult;
}
