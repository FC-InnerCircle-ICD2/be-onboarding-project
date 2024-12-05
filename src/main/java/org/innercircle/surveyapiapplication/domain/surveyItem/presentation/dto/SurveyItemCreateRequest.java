package org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.LongAnswerSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ShortAnswerSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SingleChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.domain.surveyItem.application.SurveyItemIdGenerator;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.List;

public record SurveyItemCreateRequest(
    String name,
    String description,
    boolean required,
    SurveyItemType type,
    List<String> options
) {
    public SurveyItem toDomain() {
        Long questionId = SurveyItemIdGenerator.generateId();
        return switch (type) {
            case SHORT_ANSWER -> new ShortAnswerSurveyItem(questionId, name, description, required);
            case LONG_ANSWER -> new LongAnswerSurveyItem(questionId, name, description, required);
            case SINGLE_CHOICE_ANSWER -> new SingleChoiceSurveyItem(questionId, name, description, required, options);
            case MULTI_CHOICE_ANSWER -> new MultiChoiceSurveyItem(questionId, name, description, required, options);
            default -> throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
        };
    }
}
