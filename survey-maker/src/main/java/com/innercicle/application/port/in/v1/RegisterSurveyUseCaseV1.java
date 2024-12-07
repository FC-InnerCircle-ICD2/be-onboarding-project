package com.innercicle.application.port.in.v1;

import com.innercicle.domain.v1.Survey;

public interface RegisterSurveyUseCaseV1 {

    Survey registerSurvey(RegisterSurveyCommandV1 command);

}
