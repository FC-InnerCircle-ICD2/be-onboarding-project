package org.innercircle.surveyapiapplication.domain.survey.fixture;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;

import java.util.List;

public class SurveyFixture {

    public static Survey createSurvey(List<SurveyItem> surveyItems) {
        return new Survey(1L, "설문조사이름", "설문조사설명", surveyItems);
    }

    public static Survey createSurvey(Long surveyId, List<SurveyItem> surveyItems) {
        return new Survey(surveyId, "설문조사이름", "설문조사설명", surveyItems);
    }

}
