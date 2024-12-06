package org.innercircle.surveyapiapplication.domain.surveySubmission.presentation.dto;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.MultiChoiceSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.ParagraphSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SingleChoiceSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.TextSurveySubmission;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.List;

public record SurveySubmissionCreateRequest(Object response)
{
    public SurveySubmission<?> toDomain(SurveyItemType type) {
        validateResponse(type, response);
        return switch (type) {
            case TEXT -> new TextSurveySubmission((String) response);
            case PARAGRAPH -> new ParagraphSurveySubmission((String) response);
            case SINGLE_CHOICE_ANSWER -> new SingleChoiceSurveySubmission((String) response);
            case MULTI_CHOICE_ANSWER -> new MultiChoiceSurveySubmission((List<String>) response);
            default -> throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
        };

    }

    private void validateResponse(SurveyItemType type, Object response) {
        switch (type) {
            case TEXT, PARAGRAPH, SINGLE_CHOICE_ANSWER -> {
                if (!(response instanceof String)) {
                    throw new IllegalArgumentException("Response must be a String for type: " + type);
                }
            }
            case MULTI_CHOICE_ANSWER -> {
                if (!(response instanceof List)) {
                    throw new IllegalArgumentException("Response must be a List for type: " + type);
                }
            }
            default -> throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
        }
    }
}
