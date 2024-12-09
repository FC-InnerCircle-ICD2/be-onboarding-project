package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.presentation.dto.SurveySubmissionInquiryResponse;

import java.util.List;
import java.util.stream.Collectors;

public record SurveyItemAndSubmissionInquiryResponse(
    Long surveyItemId,
    int surveyItemVersion,
    String surveyItemName,
    String surveyItemDescription,
    SurveyItemType type,
    boolean required,
    List<SurveySubmissionInquiryResponse> submissionInquiryResponses
) {

    public static SurveyItemAndSubmissionInquiryResponse from(SurveyItem surveyItem, List<SurveySubmission<?>> surveySubmissions) {
        return new SurveyItemAndSubmissionInquiryResponse(
            surveyItem.getId(),
            surveyItem.getVersion(),
            surveyItem.getName(),
            surveyItem.getDescription(),
            surveyItem.getType(),
            surveyItem.isRequired(),
            surveySubmissions.stream().map(SurveySubmissionInquiryResponse::from).collect(Collectors.toList())
        );
    }

}
