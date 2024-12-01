package com.innercicle.adapter.out.persistence.survey.v1;

import com.innercicle.adapter.out.persistence.survey.entity.SurveyEntity;
import com.innercicle.adapter.out.persistence.survey.repository.SurveyRepository;
import com.innercicle.annotations.PersistenceAdapter;
import com.innercicle.application.port.out.v1.RegisterSurveyOutPortV1;
import com.innercicle.domain.v1.Survey;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class SurveyOutPortAdapterV1 implements RegisterSurveyOutPortV1 {

    private final SurveyRepository surveyRepository;

    @Override
    public Survey registerSurvey(Survey survey) {
        SurveyEntity surveyEntity = SurveyEntity.from(survey);
        surveyRepository.save(surveyEntity);
        return surveyEntity.mapToDomain();
    }

}
