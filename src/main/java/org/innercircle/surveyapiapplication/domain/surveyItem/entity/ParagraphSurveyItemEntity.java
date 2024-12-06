package org.innercircle.surveyapiapplication.domain.surveyItem.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ParagraphSurveyItem;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PARAGRAPH")
public class ParagraphSurveyItemEntity extends SurveyItemEntity {

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
