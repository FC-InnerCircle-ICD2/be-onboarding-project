package org.innercircle.surveyapiapplication.domain.surveySubmission.infrastructure;

import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;

import java.util.List;

public interface SurveySubmissionRepository {

    SurveySubmission<?> save(SurveySubmission<?> surveySubmission);

    List<SurveySubmission<?>> findBySurveyItemIdAndVersion(Long surveyItemId, int surveyItemVersion);

}
