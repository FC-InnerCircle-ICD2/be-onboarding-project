package org.innercircle.surveyapiapplication.domain.surveySubmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;

@Entity
@Table(name = "survey_submission")
@AllArgsConstructor
@NoArgsConstructor
public class SurveySubmissionEntity<T> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "question_version", nullable = false)
    private int questionVersion;

    @Column(name = "response", nullable = false)
    private T response;

    public static <T> SurveySubmissionEntity<T> from(SurveySubmission<T> surveySubmission) {
        return new SurveySubmissionEntity<>(
            surveySubmission.getId(),
            surveySubmission.getSurveyItemId(),
            surveySubmission.getSurveyItemVersion(),
            surveySubmission.getResponse()
        );
    }

    public SurveySubmission<T> toDomain() {
        return new SurveySubmission<>(this.id, this.questionId, this.questionVersion, this.response);
    }

}
