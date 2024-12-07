package org.innercircle.surveyapiapplication.domain.survey.infrastructure;

import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;

public interface SurveyRepository {

    Survey save(Survey survey);

    Survey findById(Long surveyId);

}
