package org.survey.api.domain.survey.business;

import lombok.RequiredArgsConstructor;
import org.survey.api.common.annotation.Business;
import org.survey.api.domain.survey.controller.model.*;
import org.survey.api.domain.survey.converter.SurveyConverter;
import org.survey.api.domain.survey.service.SurveyService;
import org.survey.db.selectlist.SelectListEntity;
import org.survey.db.surveyitem.ItemInputType;
import org.survey.db.surveyitem.SurveyItemEntity;

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

    public SurveyBaseResponse find(Long id){
        var baseEntity = surveyService.baseFind(id);
        var itemEntityList = surveyService.itemFind(baseEntity.getId());
        List<SurveyItemResponse> itemResponseList = new ArrayList<>();
        for(SurveyItemEntity itemEntity : itemEntityList){
            List<String> selectList = new ArrayList<>();
            if(itemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || itemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                var selectListEntityList = surveyService.selectListFind(
                        baseEntity.getId(),
                        itemEntity.getId()
                );
                for(SelectListEntity selectListEntity : selectListEntityList){
                    selectList.add(selectListEntity.getContent());
                }
            }
            var itemResponse = surveyConverter.toResponse(itemEntity, selectList);
            itemResponseList.add(itemResponse);
        }
        var response = surveyConverter.toResponse(baseEntity, itemResponseList);
        return response;
    }

    public List<SurveyListResponse> baseAllFind(){
        var baseEntityList = surveyService.baseAllFind();
        return surveyConverter.toResponse(baseEntityList);
    }

    public SurveyListResponse deleteSurvey(Long id){
        var baseEntity = surveyService.deleteSurvey(id);
        return surveyConverter.toResponse(baseEntity);
    }
}
