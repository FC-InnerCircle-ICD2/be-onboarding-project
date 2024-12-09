package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemInquiryResponse;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;

import java.util.List;
import java.util.stream.Collectors;

public record SurveyInquiryResponse(
    Long id,
    String name,
    String description,
    List<SurveyItemInquiryResponse> questionResponses
) {

    public static SurveyInquiryResponse of(Long id, String name, String description, List<SurveyItemInquiryResponse> questionResponses) {
        return new SurveyInquiryResponse(
            id,
            name,
            description,
            questionResponses
        );
    }

    public static SurveyInquiryResponse from(Survey survey) {
        return new SurveyInquiryResponse(
            survey.getId(),
            survey.getName(),
            survey.getDescription(),
            survey.getSurveyItems().stream().map(SurveyItemInquiryResponse::from).collect(Collectors.toList())
        );
    }

}
