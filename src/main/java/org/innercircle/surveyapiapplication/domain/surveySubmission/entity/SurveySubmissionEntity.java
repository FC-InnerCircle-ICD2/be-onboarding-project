package org.innercircle.surveyapiapplication.domain.surveySubmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.MultiChoiceSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.ParagraphSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SingleChoiceSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.TextSurveySubmission;
import org.innercircle.surveyapiapplication.global.entity.BaseEntity;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

@Entity
@Table(name = "survey_submissions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
@Getter
public abstract class SurveySubmissionEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "survey_item_id", nullable = false)
    private Long surveyItemId;

    @Column(name = "survey_item_version", nullable = false)
    private int surveyItemVersion;

    public SurveySubmissionEntity(Long id, Long surveyItemId, int surveyItemVersion) {
        this.id = id;
        this.surveyItemId = surveyItemId;
        this.surveyItemVersion = surveyItemVersion;
    }

    public SurveySubmissionEntity(Long surveyItemId, int surveyItemVersion) {
        this.surveyItemId = surveyItemId;
        this.surveyItemVersion = surveyItemVersion;
    }

    public static SurveySubmissionEntity from(SurveySubmission<?> surveySubmission) {
        if (surveySubmission instanceof TextSurveySubmission textSurveySubmission) {
            return new TextSurveySubmissionEntity(
                textSurveySubmission.getSurveyItemId(),
                textSurveySubmission.getSurveyItemVersion(),
                textSurveySubmission.getResponse()
            );
        }
        if (surveySubmission instanceof ParagraphSurveySubmission paragraphSurveySubmission) {
            return new ParagraphSurveySubmissionEntity(
                paragraphSurveySubmission.getSurveyItemId(),
                paragraphSurveySubmission.getSurveyItemVersion(),
                paragraphSurveySubmission.getResponse()
            );
        }
        if (surveySubmission instanceof SingleChoiceSurveySubmission singleChoiceSurveySubmission) {
            return new SingleChoiceSurveySubmissionEntity(
                singleChoiceSurveySubmission.getSurveyItemId(),
                singleChoiceSurveySubmission.getSurveyItemVersion(),
                singleChoiceSurveySubmission.getResponse()
            );
        }
        if (surveySubmission instanceof MultiChoiceSurveySubmission multiChoiceSurveySubmission) {
            return new MultiChoiceSurveySubmissionEntity(
                multiChoiceSurveySubmission.getSurveyItemId(),
                multiChoiceSurveySubmission.getSurveyItemVersion(),
                multiChoiceSurveySubmission.getResponse()
            );
        }
        throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
    }

    public abstract SurveySubmission<?> toDomain();

}
