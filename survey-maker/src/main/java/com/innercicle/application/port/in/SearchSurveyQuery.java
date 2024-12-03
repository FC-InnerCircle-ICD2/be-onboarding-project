package com.innercicle.application.port.in;

import lombok.Getter;

@Getter
public class SearchSurveyQuery {

    private Long surveyId;

    public static SearchSurveyQuery of(Long surveyId) {
        SearchSurveyQuery searchSurveyQuery = new SearchSurveyQuery();
        searchSurveyQuery.surveyId = surveyId;
        return searchSurveyQuery;
    }

}
