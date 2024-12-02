package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;

import java.util.List;

public record SurveyCreateRequest(
    String name,
    String description,
    List<QuestionCreateRequest> questionCreateRequests
) {
    public Survey toDomain() {
        return new Survey(name, description, questionCreateRequests.stream().map(QuestionCreateRequest::toDomain).toList());
    }
}
