package org.survey.api.domain.survey.business;

import lombok.RequiredArgsConstructor;
import org.survey.api.common.annotation.Business;
import org.survey.api.domain.survey.controller.model.SurveyBaseRequest;
import org.survey.api.domain.survey.controller.model.SurveyBaseResponse;
import org.survey.api.domain.survey.controller.model.SurveyItemRequest;
import org.survey.api.domain.survey.controller.model.SurveyItemResponse;
import org.survey.api.domain.survey.converter.SurveyConverter;
import org.survey.api.domain.survey.service.SurveyService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Business
public class SurveyBusiness {

    private final SurveyService surveyService;
    private final SurveyConverter surveyConverter;

    public SurveyBaseResponse register(SurveyBaseRequest request){
        var baseEntity = surveyConverter.toEntity(request);
        var newBaseEntity = surveyService.baseRegister(baseEntity);
        List<SurveyItemResponse> items = new ArrayList<>();
        for(SurveyItemRequest item : request.getItems()){
            var itemEntity = surveyConverter.toEntity(item, newBaseEntity.getId());
            var newItemEntity = surveyService.itemRegister(itemEntity);
            for(String options : item.getSelectOptions()){
                var selectListEntity = surveyConverter.toEntity(
                        newBaseEntity.getId(),
                        newItemEntity.getId(),
                        options);
                var newSelectListEntity = surveyService.selectListRegister(selectListEntity);
            }
            var itemResponse = surveyConverter.toResponse(newItemEntity, item.getSelectOptions());
            items.add(itemResponse);
        }
        var response = surveyConverter.toResponse(newBaseEntity, items);
        return response;
    }
}
