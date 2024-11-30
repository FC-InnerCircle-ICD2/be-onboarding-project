package org.innercircle.surveyapiapplication.domain.question.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomExceptionStatus;

import java.util.List;

@Getter
public class SingleChoiceQuestion extends Question {

    private List<String> options;

    private Answer answer;

    public SingleChoiceQuestion(Long id, String name, String description, boolean required, Long surveyId, List<String> options, Answer answer) {
        super(id, name, description, required, surveyId);
        this.options = options;
        this.answer = answer;
    }

    public SingleChoiceQuestion(Long id, String name, String description, boolean required, Long surveyId, List<String> options) {
        super(id, name, description, required, surveyId);
        this.options = options;
    }

    public SingleChoiceQuestion(Long id, String name, String description, boolean required, List<String> options) {
        super(id, name, description, required);
        this.options = options;
    }

    @Override
    public void answer(Answer answer) {
        if (!options.contains(answer.getResponse())) {
            throw new CustomException(CustomExceptionStatus.NOT_FOUND_QUESTION_OPTION);
        }
        this.answer = answer;
        answer.setQuestionId(this.getId());
    }

}
