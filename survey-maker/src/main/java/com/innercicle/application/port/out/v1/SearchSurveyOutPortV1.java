package com.innercicle.application.port.out.v1;

import com.innercicle.application.port.in.v1.SearchSurveyQueryV1;
import com.innercicle.domain.v1.Survey;

public interface SearchSurveyOutPortV1 {

    Survey searchSurvey(SearchSurveyQueryV1 searchSurveyQueryV1);

}
