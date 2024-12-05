package org.innercircle.surveyapiapplication.domain.surveyItem.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.answer.entity.AnswerEntity;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.LongAnswerSurveyItem;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("LONG_ANSWER")
public class LongAnswerSurveyItemEntity extends SurveyItemEntity {

    @Transient
    private AnswerEntity answerEntity;

    public LongAnswerSurveyItemEntity(Long id, int version, String name, String description, boolean required, Long surveyId) {
        super(id, version, name, description, required, surveyId);
    }

    @Override
    public LongAnswerSurveyItem toDomain() {
        return new LongAnswerSurveyItem(
            this.getSurveyItemId().getId(),
            this.getSurveyItemId().getVersion(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId()
        );
    }

}
