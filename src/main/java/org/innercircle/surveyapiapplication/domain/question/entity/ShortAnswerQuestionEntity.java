package org.innercircle.surveyapiapplication.domain.question.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.answer.entity.AnswerEntity;
import org.innercircle.surveyapiapplication.domain.question.domain.ShortAnswerQuestion;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("SHORT_ANSWER")
public class ShortAnswerQuestionEntity extends QuestionEntity {

    @Transient
    private AnswerEntity answerEntity;

    public ShortAnswerQuestionEntity(String name, String description, boolean required, Long surveyId, AnswerEntity answerEntity) {
        super(name, description, required, surveyId);
        this.answerEntity = answerEntity;
    }

    public ShortAnswerQuestionEntity(String name, String description, boolean required, Long surveyId) {
        super(name, description, required, surveyId);
    }

    @Override
    public ShortAnswerQuestion toDomain() {
        return new ShortAnswerQuestion(
            this.getId(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId()
        );
    }

}
