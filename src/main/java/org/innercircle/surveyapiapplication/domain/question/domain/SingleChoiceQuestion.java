package org.innercircle.surveyapiapplication.domain.question.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;
import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.List;

@Getter
public class SingleChoiceQuestion extends Question {

    private List<String> options;

    private Answer answer;

    public SingleChoiceQuestion(Long id, int version, String name, String description, boolean required, Long surveyId, List<String> options) {
        super(id, version, name, description, required, surveyId);
        this.options = options;
    }

    public SingleChoiceQuestion(Long id, String name, String description, boolean required, List<String> options) {
        super(id, 1, name, description, required);
        this.options = options;
    }

    @Override
    public void answer(Answer answer) {
        if (!options.contains(answer.getResponse())) {
            throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_OPTION);
        }
        this.answer = answer;
        answer.setQuestionId(this.getId());
    }

    @Override
    public QuestionType getType() {
        return QuestionType.SINGLE_CHOICE_ANSWER;
    }

}
