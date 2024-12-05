package org.innercircle.surveyapiapplication.domain.surveyItem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.LongAnswerSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ShortAnswerSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SingleChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.entity.id.SurveyItemId;
import org.innercircle.surveyapiapplication.global.entity.BaseEntity;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

@Entity
@Table(name = "survey_items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
@Getter
public abstract class SurveyItemEntity extends BaseEntity {

    @Id
    private SurveyItemId surveyItemId;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 1000, nullable = true)
    private String description;

    @Column(nullable = false)
    private boolean required;

    @Column(name = "survey_id", nullable = false)
    private Long surveyId;

    public SurveyItemEntity(Long questionId, int questionVersion, String name, String description, boolean required, Long surveyId) {
        this.surveyItemId = new SurveyItemId(questionId, questionVersion);
        this.name = name;
        this.description = description;
        this.required = required;
        this.surveyId = surveyId;
    }

    public static SurveyItemEntity from(SurveyItem surveyItem) {
        if (surveyItem instanceof ShortAnswerSurveyItem) {
            return new ShortAnswerSurveyItemEntity(surveyItem.getId(), surveyItem.getVersion(), surveyItem.getName(), surveyItem.getDescription(), surveyItem.isRequired(), surveyItem.getSurveyId());
        }
        if (surveyItem instanceof LongAnswerSurveyItem) {
            return new LongAnswerSurveyItemEntity(surveyItem.getId(), surveyItem.getVersion(), surveyItem.getName(), surveyItem.getDescription(), surveyItem.isRequired(), surveyItem.getSurveyId());
        }
        if (surveyItem instanceof SingleChoiceSurveyItem) {
            return new SingleChoiceSurveyItemEntity(surveyItem.getId(), surveyItem.getVersion(), surveyItem.getName(), surveyItem.getDescription(), surveyItem.isRequired(), surveyItem.getSurveyId(), ((SingleChoiceSurveyItem) surveyItem).getOptions());
        }
        if (surveyItem instanceof MultiChoiceSurveyItem) {
            return new MultiChoiceSurveyItemEntity(surveyItem.getId(), surveyItem.getVersion(), surveyItem.getName(), surveyItem.getDescription(), surveyItem.isRequired(), surveyItem.getSurveyId(), ((MultiChoiceSurveyItem) surveyItem).getOptions());
        }
        throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
    }

    public abstract SurveyItem toDomain();

}
