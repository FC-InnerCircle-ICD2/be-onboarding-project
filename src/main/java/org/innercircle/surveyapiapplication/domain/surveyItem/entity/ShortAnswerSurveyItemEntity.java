package org.innercircle.surveyapiapplication.domain.surveyItem.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.answer.entity.AnswerEntity;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ShortAnswerSurveyItem;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("SHORT_ANSWER")
public class ShortAnswerSurveyItemEntity extends SurveyItemEntity {

    @Transient
    private AnswerEntity answerEntity;

    public ShortAnswerSurveyItemEntity(Long questionId, int questionVersion, String name, String description, boolean required, Long surveyId) {
        super(questionId, questionVersion, name, description, required, surveyId);
    }

    @Override
    public ShortAnswerSurveyItem toDomain() {
        return new ShortAnswerSurveyItem(
            this.getSurveyItemId().getId(),
            this.getSurveyItemId().getVersion(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId()
        );
    }

}
