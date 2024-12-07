package net.gentledot.survey.application.service.out;


import net.gentledot.survey.domain.surveyanswer.SurveyAnswer;

import java.util.List;

public interface SurveyAnswerRepository {
    SurveyAnswer save(SurveyAnswer surveyAnswer);

    List<SurveyAnswer> findAllBySurveyId(String surveyId);
}
