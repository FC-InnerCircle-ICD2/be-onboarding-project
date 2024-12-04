package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.question.domain.MultiChoiceQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.domain.SingleChoiceQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.ArrayList;
import java.util.List;

public record QuestionInquiryResponse(
    Long id,
    int version,
    String name,
    String description,
    QuestionType type,
    boolean required,
    List<String> options
) {

    public static QuestionInquiryResponse from(Question question) {
        return switch (question.getType()) {
            case SHORT_ANSWER, LONG_ANSWER -> new QuestionInquiryResponse(question.getId(),
                question.getVersion(),
                question.getName(),
                question.getDescription(),
                question.getType(),
                question.isRequired(),
                new ArrayList<>()
            );
            case SINGLE_CHOICE_ANSWER -> new QuestionInquiryResponse(question.getId(),
                question.getVersion(),
                question.getName(),
                question.getDescription(),
                question.getType(),
                question.isRequired(),
                ((SingleChoiceQuestion) question).getOptions()
            );
            case MULTI_CHOICE_ANSWER -> new QuestionInquiryResponse(question.getId(),
                question.getVersion(),
                question.getName(),
                question.getDescription(),
                question.getType(),
                question.isRequired(),
                ((MultiChoiceQuestion) question).getOptions()
            );
            default -> throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
        };
    }
}
