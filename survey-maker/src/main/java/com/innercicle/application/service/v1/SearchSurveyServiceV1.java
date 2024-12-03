package com.innercicle.application.service.v1;

import com.innercicle.annotations.UseCase;
import com.innercicle.application.port.in.SearchSurveyQuery;
import com.innercicle.application.port.in.SearchSurveyUseCaseV1;
import com.innercicle.application.port.out.SearchSurveyOutPort;
import com.innercicle.domain.v1.Survey;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SearchSurveyServiceV1 implements SearchSurveyUseCaseV1 {

    private final SearchSurveyOutPort searchSurveyOutPort;

    @Override
    public Survey searchSurvey(SearchSurveyQuery searchSurveyQuery) {
        return searchSurveyOutPort.searchSurvey(searchSurveyQuery);
    }

}
