package com.innercicle.application.port.out.v1;

import com.innercicle.adapter.out.service.v1.dto.Survey;

public interface SearchSurveyOutPortV1 {

    Survey getSurvey(Long surveyId);

}
