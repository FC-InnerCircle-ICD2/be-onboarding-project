package net.gentledot.survey.application.service.out;


import net.gentledot.survey.application.service.in.model.request.SubmitSurveyAnswer;
import net.gentledot.survey.domain.surveyanswer.SurveyAnswer;

import java.util.List;

public interface SurveyAnswerRepository {
    SurveyAnswer save(String surveyId, List<SubmitSurveyAnswer> answers);

    List<SurveyAnswer> findAllBySurveyId(String surveyId);
}
