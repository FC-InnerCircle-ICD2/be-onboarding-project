package com.innercicle.application.port.out.v1;

import com.innercicle.domain.ReplySurvey;

import java.util.List;

public interface SearchReplySurveyOutPortV1 {

    ReplySurvey searchReplySurvey(Long replySurveyId);

    List<ReplySurvey> searchRepliesSurvey(Long replySurveyId, String searchKeyword);

}
