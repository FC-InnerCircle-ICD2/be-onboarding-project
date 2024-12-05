package com.innercicle.adapter.out.service.v1.dto;

import com.innercicle.annotations.OutPortAdapter;
import com.innercicle.application.port.out.v1.SearchSurveyOutPortV1;
import lombok.RequiredArgsConstructor;

@OutPortAdapter
@RequiredArgsConstructor
public class SurveyOutPortAdapter implements SearchSurveyOutPortV1 {

    private final SurveyMakerService surveyMakerService;

    @Override
    public Survey getSurvey(Long surveyId) {
        return surveyMakerService.getSurvey(surveyId);
    }

}
