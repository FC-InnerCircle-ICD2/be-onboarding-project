package com.innercicle.application.port.out;

import com.innercicle.application.port.in.SearchSurveyQuery;
import com.innercicle.domain.v1.Survey;

public interface SearchSurveyOutPort {

    Survey searchSurvey(SearchSurveyQuery searchSurveyQuery);

}
