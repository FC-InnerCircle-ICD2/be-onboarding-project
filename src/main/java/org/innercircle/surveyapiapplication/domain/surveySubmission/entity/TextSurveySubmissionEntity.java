package org.innercircle.surveyapiapplication.domain.surveySubmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.TextSurveySubmission;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TEXT")
public class TextSurveySubmissionEntity extends SurveySubmissionEntity {

    @Column(name = "text")
    private String response;

    public TextSurveySubmissionEntity(Long surveyItemId, int surveyItemVersion, String response) {
        super(surveyItemId, surveyItemVersion);
        this.response = response;
    }

    @Override
    public SurveySubmission<?> toDomain() {
        return new TextSurveySubmission(
            this.getId(),
            this.getSurveyItemId(),
            this.getSurveyItemVersion(),
            this.getResponse()
        );
    }

}
