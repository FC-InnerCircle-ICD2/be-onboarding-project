package net.gentledot.survey.service.out;

import net.gentledot.survey.domain.surveybase.Survey;

public interface SurveyRepository {
    Survey findById(String surveyId);

    Survey save(Survey survey);
}
