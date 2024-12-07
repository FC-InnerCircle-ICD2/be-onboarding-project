package net.gentledot.survey.application.service.out;

import net.gentledot.survey.domain.surveybase.Survey;

public interface SurveyRepository {
    Survey findById(String surveyId);

    Survey save(Survey survey);

    boolean existsById(String surveyId);
}
