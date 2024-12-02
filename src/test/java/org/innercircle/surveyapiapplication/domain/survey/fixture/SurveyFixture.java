package org.innercircle.surveyapiapplication.domain.survey.fixture;

import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;

import java.util.List;

public class SurveyFixture {

    public static Survey createSurvey(List<Question> questions) {
        return new Survey(1L, "설문조사이름", "설문조사설명", questions);
    }

    public static Survey createSurvey(Long surveyId, List<Question> questions) {
        return new Survey(surveyId, "설문조사이름", "설문조사설명", questions);
    }

}
