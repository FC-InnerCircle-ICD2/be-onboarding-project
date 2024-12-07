package net.gentledot.survey.service.out;


import net.gentledot.survey.domain.surveyanswer.SurveyAnswer;
import net.gentledot.survey.service.in.model.request.SubmitSurveyAnswer;

import java.util.List;

public interface SurveyAnswerRepository {
    SurveyAnswer save(String surveyId, List<SubmitSurveyAnswer> answers);

    List<SurveyAnswer> findAllBySurveyId(String surveyId);
}
