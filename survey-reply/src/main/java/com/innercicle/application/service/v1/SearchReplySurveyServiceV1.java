package com.innercicle.application.service.v1;

import com.innercicle.annotations.UseCase;
import com.innercicle.application.port.in.v1.SearchReplySurveyQuery;
import com.innercicle.application.port.in.v1.SearchReplySurveyUsecaseV1;
import com.innercicle.application.port.out.v1.SearchReplySurveyOutPortV1;
import com.innercicle.domain.ReplySurvey;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SearchReplySurveyServiceV1 implements SearchReplySurveyUsecaseV1 {

    private final SearchReplySurveyOutPortV1 searchReplySurveyOutPortV1;

    @Override
    public ReplySurvey searchReplySurvey(SearchReplySurveyQuery searchReplySurveyQuery) {
        return searchReplySurveyOutPortV1.searchReplySurvey(searchReplySurveyQuery.getReplySurveyId());
    }

}
