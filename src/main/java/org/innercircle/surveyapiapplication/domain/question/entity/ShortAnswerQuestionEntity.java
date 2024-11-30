package org.innercircle.surveyapiapplication.domain.question.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;
import org.innercircle.surveyapiapplication.domain.question.domain.ShortAnswerQuestion;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("SHORT_ANSWER")
public class ShortAnswerQuestionEntity extends QuestionEntity {

    @Transient
    private Answer answer;

    public ShortAnswerQuestionEntity(String name, String description, boolean required, Long surveyId, Answer answer) {
        super(name, description, required, surveyId);
        this.answer = answer;
    }

    @Override
    public ShortAnswerQuestion toDomain() {
        return new ShortAnswerQuestion(
            this.getId(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId(),
            this.getAnswer()
        );
    }

}
