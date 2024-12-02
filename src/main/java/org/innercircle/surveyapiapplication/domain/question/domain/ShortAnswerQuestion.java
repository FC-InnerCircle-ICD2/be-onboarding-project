package org.innercircle.surveyapiapplication.domain.question.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;
import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;

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

    public ShortAnswerQuestion(String name, String description, boolean required) {
        super(name, description, required);
    }

    @Override
    public void answer(Answer answer) {
        this.answer = answer;
        answer.setQuestionId(this.getId());
    }

    @Override
    public QuestionType getType() {
        return QuestionType.SHORT_ANSWER;
    }

}
