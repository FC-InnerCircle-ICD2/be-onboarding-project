package org.survey.api.domain.survey.business;

import lombok.RequiredArgsConstructor;
import org.survey.api.common.annotation.Business;
import org.survey.api.domain.survey.controller.model.SurveyBaseRequest;
import org.survey.api.domain.survey.controller.model.SurveyBaseResponse;
import org.survey.api.domain.survey.converter.SurveyConverter;
import org.survey.api.domain.survey.service.SurveyService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class SurveyBusiness {

    private final SurveyService surveyService;
    private final SurveyConverter surveyConverter;

    private SurveyBaseResponse register(SurveyBaseRequest request){
        var baseEntity = surveyConverter.toEntity(request);
        var newBaseEntity = surveyService.baseRegister(baseEntity);
        var itemList = request.getItems().stream()
                .map(surveyConverter::toEntity)
                .collect(Collectors.toList());
        var newItemList = itemList.stream()
                .map(surveyService::itemRegister)
                .collect(Collectors.toList());
        var response = surveyConverter.toResponse(newBaseEntity);
        return response;
    }
}
