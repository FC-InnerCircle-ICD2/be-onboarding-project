package com.innercicle.application.service.v1;

import com.innercicle.annotations.RedissonLock;
import com.innercicle.annotations.UseCase;
import com.innercicle.application.port.in.v1.ModifySurveyCommandV1;
import com.innercicle.application.port.in.v1.ModifySurveyUseCaseV1;
import com.innercicle.application.port.out.v1.ModifySurveyOutPortV1;
import com.innercicle.domain.v1.Survey;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ModifySurveyServiceV1 implements ModifySurveyUseCaseV1 {

    private final ModifySurveyOutPortV1 modifySurveyOutPortV1;

    @Override
    @RedissonLock("#commandV1.getId()")
    public Survey modifySurvey(ModifySurveyCommandV1 commandV1) {
        return modifySurveyOutPortV1.modifySurvey(commandV1.mapToDomain());
    }

}
