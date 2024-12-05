package org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.innercircle.surveyapiapplication.domain.surveyItem.application.SurveyItemIdGenerator;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ParagraphSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.TextSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SingleChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.List;

@JsonSerialize
public record SurveyItemCreateRequest(
    String name,
    String description,
    boolean required,
    SurveyItemType type,
    List<String> options
) {
    public SurveyItem toDomain() {
        Long surveyItemId = SurveyItemIdGenerator.generateId();
        return switch (type) {
            case TEXT -> new TextSurveyItem(surveyItemId, name, description, required);
            case PARAGRAPH -> new ParagraphSurveyItem(surveyItemId, name, description, required);
            case SINGLE_CHOICE_ANSWER -> new SingleChoiceSurveyItem(surveyItemId, name, description, required, options);
            case MULTI_CHOICE_ANSWER -> new MultiChoiceSurveyItem(surveyItemId, name, description, required, options);
            default -> throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
        };
    }

}
