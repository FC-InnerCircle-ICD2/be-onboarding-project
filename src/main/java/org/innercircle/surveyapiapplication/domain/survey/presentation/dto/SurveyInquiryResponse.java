package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;

import java.util.List;

public record SurveyInquiryResponse(
    Long id,
    String name,
    String description,
    List<QuestionInquiryResponse> questionResponses
) {

    public static SurveyInquiryResponse of(Long id, String name, String description, List<QuestionInquiryResponse> questionResponses) {
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
            survey.getQuestions().stream().map(QuestionInquiryResponse::from).toList()
        );
    }

}
