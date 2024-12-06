package com.innercicle.application.port.in.v1;

import com.innercicle.adapter.in.web.reply.v1.request.SearchRepliesSurveyQuery;
import com.innercicle.domain.ReplySurvey;

import java.util.List;

public interface SearchReplySurveyUsecaseV1 {

    ReplySurvey searchReplySurvey(SearchReplySurveyQuery searchReplySurveyQuery);

    List<ReplySurvey> searchRepliesSurvey(SearchRepliesSurveyQuery searchRepliesSurveyQuery);

}
