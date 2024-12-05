package org.innercircle.surveyapiapplication.domain.surveyItem.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.entity.SurveySubmissionEntity;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.TextSurveyItem;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TEXT")
public class TextSurveyItemEntity extends SurveyItemEntity {

    @Transient
    private SurveySubmissionEntity surveySubmissionEntity;

    public TextSurveyItemEntity(Long questionId, int questionVersion, String name, String description, boolean required, Long surveyId) {
        super(questionId, questionVersion, name, description, required, surveyId);
    }

    @Override
    public TextSurveyItem toDomain() {
        return new TextSurveyItem(
            this.getSurveyItemId().getId(),
            this.getSurveyItemId().getVersion(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId()
        );
    }

}
