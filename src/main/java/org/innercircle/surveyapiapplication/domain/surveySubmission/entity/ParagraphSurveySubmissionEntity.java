package org.innercircle.surveyapiapplication.domain.surveySubmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.ParagraphSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PARAGRAPH")
public class ParagraphSurveySubmissionEntity extends SurveySubmissionEntity {

    @Column(name = "paragraph")
    private String response;

    public ParagraphSurveySubmissionEntity(Long surveyItemId, int surveyItemVersion, String response) {
        super(surveyItemId, surveyItemVersion);
        this.response = response;
    }

    @Override
    public SurveySubmission<?> toDomain() {
        return new ParagraphSurveySubmission(
            this.getId(),
            this.getSurveyItemId(),
            this.getSurveyItemVersion(),
            this.getResponse()
        );
    }

}
