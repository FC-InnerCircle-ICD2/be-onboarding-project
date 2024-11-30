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
import java.util.HashSet;
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
            List<SelectOptionResponse> selectOptionResponseList = new ArrayList<>();
            if(newItemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || newItemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                for(SelectOptionRequest option : item.getSelectOptions()){
                    var selectListEntity = surveyConverter.toEntity(
                            option,
                            newBaseEntity.getId(),
                            newItemEntity.getId());
                    var newSelectListEntity = surveyService.selectListRegister(selectListEntity);
                    selectOptionResponseList.add(surveyConverter.toResponse(newSelectListEntity));
                }
            }
            var itemResponse = surveyConverter.toResponse(newItemEntity, selectOptionResponseList);
            items.add(itemResponse);
        }
        var response = surveyConverter.toResponse(newBaseEntity, items);
        return response;
    }

    public SurveyBaseResponse find(Long id){
        var baseEntity = surveyService.baseFindById(id);
        var itemEntityList = surveyService.itemAllFind(baseEntity.getId());
        List<SurveyItemResponse> itemResponseList = new ArrayList<>();
        for(SurveyItemEntity itemEntity : itemEntityList){
            List<SelectOptionResponse> selectOptionList = new ArrayList<>();
            if(itemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || itemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                var selectListEntityList = surveyService.selectListAllFind(
                        baseEntity.getId(),
                        itemEntity.getId()
                );
                for(SelectListEntity selectListEntity : selectListEntityList){
                    selectOptionList.add(surveyConverter.toResponse(selectListEntity));
                }
            }
            var itemResponse = surveyConverter.toResponse(itemEntity, selectOptionList);
            itemResponseList.add(itemResponse);
        }
        var response = surveyConverter.toResponse(baseEntity, itemResponseList);
        return response;
    }

    public SurveyBaseResponse updateAll(
            SurveyBaseRequest request
    ){
        var baseEntity = surveyConverter.toEntity(request);
        var newBaseEntity = surveyService.baseUpdate(request.getId(), baseEntity);
        List<SurveyItemResponse> items = new ArrayList<>();
        HashSet<Long> itemIdList = new HashSet<>();
        for(SurveyItemRequest item : request.getItems()){
            var itemEntity = surveyConverter.toEntity(item, newBaseEntity.getId());
            var newItemEntity = surveyService.itemUpdate(itemEntity, newBaseEntity.getId());
            List<SelectOptionResponse> selectOptionResponseList = new ArrayList<>();
            if(newItemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || newItemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                HashSet<Long> optionIdList = new HashSet<>();
                for(SelectOptionRequest option : item.getSelectOptions()){
                    var selectListEntity = surveyConverter.toEntity(
                            option,
                            newBaseEntity.getId(),
                            newItemEntity.getId());
                    var newSelectListEntity = surveyService.selectListUpdate(
                            selectListEntity,
                            newBaseEntity.getId(),
                            newItemEntity.getId());
                    var optionResponse = surveyConverter.toResponse(newSelectListEntity);
                    selectOptionResponseList.add(optionResponse);
                    optionIdList.add(optionResponse.getId());
                }
                List<SelectListEntity> currentSelectList = surveyService.selectListAllFind(
                        newBaseEntity.getId(),
                        newItemEntity.getId());
                for(SelectListEntity currentOption : currentSelectList){
                    if(!optionIdList.contains(currentOption.getId())){
                        surveyService.selectListDelete(
                                currentOption.getId(),
                                currentOption.getSurveyId(),
                                currentOption.getItemId());
                    }
                }
            }
            var itemResponse = surveyConverter.toResponse(newItemEntity, selectOptionResponseList);
            items.add(itemResponse);
            itemIdList.add(itemResponse.getId());
        }
        List<SurveyItemEntity> currentItemList = surveyService.itemAllFind(newBaseEntity.getId());
        for(SurveyItemEntity currentItem : currentItemList){
            if(!itemIdList.contains(currentItem.getId())){
                surveyService.itemDelete(
                        currentItem.getId(),
                        currentItem.getSurveyId());
            }
        }
        var response = surveyConverter.toResponse(newBaseEntity, items);
        return response;
    }

    public List<SurveyListResponse> baseAllFind(){
        var baseEntityList = surveyService.baseAllFind();
        return surveyConverter.toResponse(baseEntityList);
    }

    public SurveyListResponse deleteSurvey(Long id){
        var baseEntity = surveyService.baseDelete(id);
        return surveyConverter.toResponse(baseEntity);
    }
}
