package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;

public record QuestionInquiryResponse(
    Long id,
    String name,
    String description,
    QuestionType type,
    boolean required
) {

    public static QuestionInquiryResponse of(Long id, String name, String description, QuestionType type, boolean required) {
        return new QuestionInquiryResponse(
            id,
            name,
            description,
            type,
            required
        );
    }

    public static QuestionInquiryResponse from(Question question) {
        return new QuestionInquiryResponse(
            question.getId(),
            question.getName(),
            question.getDescription(),
            question.getType(),
            question.isRequired()
        );
    }

}
