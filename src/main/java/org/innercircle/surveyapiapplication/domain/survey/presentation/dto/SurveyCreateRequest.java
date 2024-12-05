package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemCreateRequest;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;

import java.util.List;

public record SurveyCreateRequest(
    String name,
    String description,
    List<SurveyItemCreateRequest> surveyItemCreateRequests
) {
    public Survey toDomain() {
        return new Survey(name, description, surveyItemCreateRequests.stream().map(SurveyItemCreateRequest::toDomain).toList());
    }
}
