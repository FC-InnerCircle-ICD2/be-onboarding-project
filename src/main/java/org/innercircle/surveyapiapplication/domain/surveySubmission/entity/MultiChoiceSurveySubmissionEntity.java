package org.innercircle.surveyapiapplication.domain.surveySubmission.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.MultiChoiceSurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("MULTI_CHOICE")
public class MultiChoiceSurveySubmissionEntity extends SurveySubmissionEntity {

    @Column(name = "multi_choice")
    @ElementCollection
    @CollectionTable(name = "survey_submission_multi_choice", joinColumns = @JoinColumn(name = "submission_id"))
    private List<String> response;

    public MultiChoiceSurveySubmissionEntity(Long surveyItemId, int surveyItemVersion, List<String> response) {
        super(surveyItemId, surveyItemVersion);
        this.response = response;
    }

    @Override
    public SurveySubmission<?> toDomain() {
        return new MultiChoiceSurveySubmission(
            this.getId(),
            this.getSurveyItemId(),
            this.getSurveyItemVersion(),
            this.getResponse()
        );
    }

}
