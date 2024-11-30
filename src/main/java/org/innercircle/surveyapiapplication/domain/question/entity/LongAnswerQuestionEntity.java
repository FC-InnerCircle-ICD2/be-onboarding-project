package org.innercircle.surveyapiapplication.domain.question.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.answer.entity.AnswerEntity;
import org.innercircle.surveyapiapplication.domain.question.domain.LongAnswerQuestion;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("LONG_ANSWER")
public class LongAnswerQuestionEntity extends QuestionEntity {

    @Transient
    private AnswerEntity answerEntity;

    public LongAnswerQuestionEntity(String name, String description, boolean required, Long surveyId, AnswerEntity answerEntity) {
        super(name, description, required, surveyId);
        this.answerEntity = answerEntity;
    }

    @Override
    public LongAnswerQuestion toDomain() {
        return new LongAnswerQuestion(
            this.getId(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId(),
            this.getAnswerEntity().toDomain()
        );
    }

}
