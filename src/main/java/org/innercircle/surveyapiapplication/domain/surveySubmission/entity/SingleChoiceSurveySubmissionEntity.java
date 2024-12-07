package org.innercircle.surveyapiapplication.domain.surveySubmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SingleChoiceSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("SINGLE_CHOICE")
public class SingleChoiceSurveySubmissionEntity extends SurveySubmissionEntity {

    @Column(name = "single_choice")
    private String response;

    public SingleChoiceSurveySubmissionEntity(Long surveyItemId, int surveyItemVersion, String response) {
        super(surveyItemId, surveyItemVersion);
        this.response = response;
    }

    @Override
    public SurveySubmission<?> toDomain() {
        return new SingleChoiceSurveySubmission(
            this.getId(),
            this.getSurveyItemId(),
            this.getSurveyItemVersion(),
            this.getResponse()
        );
    }

}
