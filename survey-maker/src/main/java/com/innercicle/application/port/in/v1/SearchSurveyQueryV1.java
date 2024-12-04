package com.innercicle.application.port.in.v1;

import lombok.Getter;

@Getter
public class SearchSurveyQueryV1 {

    private Long surveyId;

    public static SearchSurveyQueryV1 of(Long surveyId) {
        SearchSurveyQueryV1 searchSurveyQueryV1 = new SearchSurveyQueryV1();
        searchSurveyQueryV1.surveyId = surveyId;
        return searchSurveyQueryV1;
    }

}
