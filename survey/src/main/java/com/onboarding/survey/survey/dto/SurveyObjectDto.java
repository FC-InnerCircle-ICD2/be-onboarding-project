package com.onboarding.survey.survey.dto;

import java.util.List;

public record SurveyObjectDto(
    String surveyName,
    String surveyDescription,
    List<QuestionDto> questions
) {

}
