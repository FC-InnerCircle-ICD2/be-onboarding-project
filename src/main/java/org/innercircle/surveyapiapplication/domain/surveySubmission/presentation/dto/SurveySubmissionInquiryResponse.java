package org.innercircle.surveyapiapplication.domain.surveySubmission.presentation.dto;

import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;

public record SurveySubmissionInquiryResponse(
    Long surveySubmissionId,
    Long surveyItemId,
    int surveyItemVersion,
    Object response
) {

    public static SurveySubmissionInquiryResponse from(SurveySubmission<?> surveySubmission) {
        return new SurveySubmissionInquiryResponse(
            surveySubmission.getId(),
            surveySubmission.getSurveyItemId(),
            surveySubmission.getSurveyItemVersion(),
            surveySubmission.getResponse()
        );
    }

}
