package com.innercicle.application.service.v1;

import com.innercicle.annotations.UseCase;
import com.innercicle.application.port.in.v1.SearchSurveyQueryV1;
import com.innercicle.application.port.in.v1.SearchSurveyUseCaseV1;
import com.innercicle.application.port.out.v1.SearchSurveyOutPortV1;
import com.innercicle.domain.v1.Survey;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SearchSurveyServiceV1 implements SearchSurveyUseCaseV1 {

    private final SearchSurveyOutPortV1 searchSurveyOutPortV1;

    @Override
    public Survey searchSurvey(SearchSurveyQueryV1 searchSurveyQueryV1) {
        return searchSurveyOutPortV1.searchSurvey(searchSurveyQueryV1);
    }

}
