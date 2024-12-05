package org.innercircle.surveyapiapplication.domain.surveyItem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.answer.entity.AnswerEntity;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("MULTI_CHOICE")
public class MultiChoiceSurveyItemEntity extends SurveyItemEntity {

    @Column(name = "options")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> options;

    @Transient
    private List<AnswerEntity> answerEntities;

    public MultiChoiceSurveyItemEntity(Long id, int version, String name, String description, boolean required, Long surveyId, List<String> options) {
        super(id, version, name, description, required, surveyId);
        this.options = options;
    }

    @Override
    public MultiChoiceSurveyItem toDomain() {
        return new MultiChoiceSurveyItem(
            this.getSurveyItemId().getId(),
            this.getSurveyItemId().getVersion(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId(),
            this.getOptions()
        );
    }

}
