package org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SingleChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.ArrayList;
import java.util.List;

public record SurveyItemInquiryResponse(
    Long id,
    int version,
    String name,
    String description,
    SurveyItemType type,
    boolean required,
    Long surveyId,
    List<String> options
) {

    public static SurveyItemInquiryResponse from(SurveyItem surveyItem) {
        return switch (surveyItem.getType()) {
            case TEXT, PARAGRAPH -> new SurveyItemInquiryResponse(surveyItem.getId(),
                surveyItem.getVersion(),
                surveyItem.getName(),
                surveyItem.getDescription(),
                surveyItem.getType(),
                surveyItem.isRequired(),
                surveyItem.getSurveyId(),
                new ArrayList<>()
            );
            case SINGLE_CHOICE_ANSWER -> new SurveyItemInquiryResponse(surveyItem.getId(),
                surveyItem.getVersion(),
                surveyItem.getName(),
                surveyItem.getDescription(),
                surveyItem.getType(),
                surveyItem.isRequired(),
                surveyItem.getSurveyId(),
                ((SingleChoiceSurveyItem) surveyItem).getOptions()
            );
            case MULTI_CHOICE_ANSWER -> new SurveyItemInquiryResponse(surveyItem.getId(),
                surveyItem.getVersion(),
                surveyItem.getName(),
                surveyItem.getDescription(),
                surveyItem.getType(),
                surveyItem.isRequired(),
                surveyItem.getSurveyId(),
                ((MultiChoiceSurveyItem) surveyItem).getOptions()
            );
            default -> throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
        };
    }
}
