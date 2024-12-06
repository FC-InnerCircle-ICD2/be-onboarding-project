package org.innercircle.surveyapiapplication.domain.surveySubmission.infrastructure;

import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;

public interface SurveySubmissionRepository {

    SurveySubmission<?> save(SurveySubmission<?> surveySubmission);

}
