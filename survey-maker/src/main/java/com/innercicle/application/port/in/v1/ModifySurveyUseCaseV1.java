package com.innercicle.application.port.in.v1;

import com.innercicle.domain.v1.Survey;

public interface ModifySurveyUseCaseV1 {

    Survey modifySurvey(ModifySurveyCommandV1 request);

}
