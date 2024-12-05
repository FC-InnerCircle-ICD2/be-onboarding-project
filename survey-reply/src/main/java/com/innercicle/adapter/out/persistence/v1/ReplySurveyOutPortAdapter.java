package com.innercicle.adapter.out.persistence.v1;

import com.innercicle.annotations.PersistenceAdapter;
import com.innercicle.application.port.out.ReplySurveyOutPortV1;
import com.innercicle.domain.ReplySurvey;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReplySurveyOutPortAdapter implements ReplySurveyOutPortV1 {

    private final ReplySurveyRepository replySurveyRepository;

    @Override
    public ReplySurvey replySurvey(ReplySurvey replySurvey) {
        ReplySurveyEntity replySurveyEntity = ReplySurveyEntity.from(replySurvey);
        replySurveyEntity = replySurveyRepository.save(replySurveyEntity);
        return replySurveyEntity.mapToDomain();
    }

}
