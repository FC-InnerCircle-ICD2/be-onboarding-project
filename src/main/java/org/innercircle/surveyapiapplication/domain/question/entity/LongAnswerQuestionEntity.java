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

    public LongAnswerQuestionEntity(Long id, int version, String name, String description, boolean required, Long surveyId) {
        super(id, version, name, description, required, surveyId);
    }

    @Override
    public LongAnswerQuestion toDomain() {
        return new LongAnswerQuestion(
            this.getQuestionId().getId(),
            this.getQuestionId().getVersion(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId()
        );
    }

}
