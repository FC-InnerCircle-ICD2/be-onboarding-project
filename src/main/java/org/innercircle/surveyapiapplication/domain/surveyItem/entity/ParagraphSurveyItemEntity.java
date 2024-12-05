package org.innercircle.surveyapiapplication.domain.surveyItem.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.entity.SurveySubmissionEntity;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ParagraphSurveyItem;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PARAGRAPH")
public class ParagraphSurveyItemEntity extends SurveyItemEntity {

    @Transient
    private SurveySubmissionEntity surveySubmissionEntity;

    public ParagraphSurveyItemEntity(Long id, int version, String name, String description, boolean required, Long surveyId) {
        super(id, version, name, description, required, surveyId);
    }

    @Override
    public ParagraphSurveyItem toDomain() {
        return new ParagraphSurveyItem(
            this.getSurveyItemId().getId(),
            this.getSurveyItemId().getVersion(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId()
        );
    }

}
