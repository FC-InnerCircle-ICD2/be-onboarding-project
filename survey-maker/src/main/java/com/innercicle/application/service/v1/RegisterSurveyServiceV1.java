package com.innercicle.application.service.v1;

import com.innercicle.annotations.UseCase;
import com.innercicle.application.port.in.v1.RegisterSurveyCommandV1;
import com.innercicle.application.port.in.v1.RegisterSurveyUseCaseV1;
import com.innercicle.application.port.out.v1.RegisterSurveyOutPortV1;
import com.innercicle.domain.v1.Survey;
import lombok.RequiredArgsConstructor;

/**
 * 설문 등록 서비스
 * <p>설문과 설문 항목을 등록 하는 서비스</p>
 *
 * @author seunggu.lee
 */
@UseCase
@RequiredArgsConstructor
public class RegisterSurveyServiceV1 implements RegisterSurveyUseCaseV1 {

    private final RegisterSurveyOutPortV1 registerSurveyOutPortV1;

    @Override
    public Survey registerSurvey(RegisterSurveyCommandV1 command) {
        Survey survey = command.mapToDomain();
        return registerSurveyOutPortV1.registerSurvey(survey);
    }

}
