package org.innercircle.surveyapiapplication.domain.survey.presentation.dto;

import org.innercircle.surveyapiapplication.domain.question.domain.LongAnswerQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.MultiChoiceQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.domain.ShortAnswerQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.SingleChoiceQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.List;

public record QuestionCreateRequest(
    String name,
    String description,
    boolean required,
    QuestionType type,
    List<String> options
) {
    public Question toDomain() {
        return switch (type) {
            case SHORT_ANSWER -> new ShortAnswerQuestion(name, description, required);
            case LONG_ANSWER -> new LongAnswerQuestion(name, description, required);
            case SINGLE_CHOICE_ANSWER -> new SingleChoiceQuestion(name, description, required, options);
            case MULTI_CHOICE_ANSWER -> new MultiChoiceQuestion(name, description, required, options);
            default -> throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
        };
    }
}
