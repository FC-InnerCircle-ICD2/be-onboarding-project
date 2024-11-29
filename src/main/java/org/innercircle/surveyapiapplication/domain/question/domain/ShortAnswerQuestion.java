package org.innercircle.surveyapiapplication.domain.question.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;

@Getter
public class ShortAnswerQuestion extends Question {

    private Answer answer;

    public ShortAnswerQuestion(Long id, String name, String description, boolean required, Long surveyId, Answer answer) {
        super(id, name, description, required, surveyId);
        this.answer = answer;
    }

    public ShortAnswerQuestion(Long id, String name, String description, boolean required, Long surveyId) {
        super(id, name, description, required, surveyId);
    }

    public ShortAnswerQuestion(Long id, String name, String description, boolean required) {
        super(id, name, description, required);
    }

    @Override
    public void answer(Answer answer) {
        this.answer = answer;
        answer.setQuestionId(this.getId());
    }

}
