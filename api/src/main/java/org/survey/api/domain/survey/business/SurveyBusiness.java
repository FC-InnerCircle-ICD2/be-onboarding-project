package org.survey.api.domain.survey.business;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.survey.api.common.annotation.Business;
import org.survey.api.common.error.SelectErrorCode;
import org.survey.api.common.exception.ApiException;
import org.survey.api.domain.survey.controller.model.*;
import org.survey.api.domain.survey.converter.SurveyConverter;
import org.survey.api.domain.survey.service.SurveyService;
import org.survey.db.selectlist.SelectListEntity;
import org.survey.db.surveyanswer.SurveyReplyEntity;
import org.survey.db.surveybase.SurveyBaseEntity;
import org.survey.db.surveyitem.ItemInputType;
import org.survey.db.surveyitem.SurveyItemEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Business
public class SurveyBusiness {

    private final SurveyService surveyService;
    private final SurveyConverter surveyConverter;

    @Transactional
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

    @Transactional
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

    @Transactional
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

    @Transactional
    public SurveyReplyListResponse reply(SurveyReplyListRequest surveyReplyListRequest){
        SurveyBaseEntity surveyBaseEntity = surveyService.baseFindById(surveyReplyListRequest.getId());
        List<SurveyReplyResponse> replies = new ArrayList<>();
        for(SurveyReplyRequest surveyReplyRequest : surveyReplyListRequest.getReply()){
            var surveyReplyEntity = surveyConverter.toEntity(surveyReplyRequest, surveyReplyListRequest.getId());
            var surveyItem = surveyService.itemFindById(surveyReplyEntity.getItemId(), surveyReplyListRequest.getId());
            if(surveyItem.getInputType() == ItemInputType.SINGLE_SELECT_LIST
            || surveyItem.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                List<SelectListEntity> selectListEntityList = surveyService.selectListAllFind(
                        surveyReplyListRequest.getId(), surveyReplyEntity.getItemId());
                Set<String> contentHashSet = new HashSet<>();
                for(SelectListEntity options : selectListEntityList){
                    contentHashSet.add(options.getContent());
                }
                if(!contentHashSet.contains(surveyReplyRequest.getContent())){
                    throw new ApiException(SelectErrorCode.NON_EXIST_REQUEST, "The request does not exist in the selection list.");
                }
            }
            if(!(surveyItem.getInputType() == ItemInputType.MULTI_SELECT_LIST)){
                List<SurveyReplyEntity> surveyReplyEntityList = surveyService.replyMultiFind(
                        surveyReplyListRequest.getId(), surveyReplyEntity.getItemId());
                if(surveyReplyEntityList.size() > 0){
                    throw new ApiException(SelectErrorCode.DUPLICATE_REQUEST, "This is a duplicate request");
                }
            }
            var newSurveyReplyEntity = surveyService.replyRegister(surveyReplyEntity);
            var surveyReplyResponse = surveyConverter.toResponse(newSurveyReplyEntity);
            replies.add(surveyReplyResponse);
        }
        var response = new SurveyReplyListResponse(surveyReplyListRequest.getId(), replies);
        return response;
    }

    @Transactional
    public List<SurveyListResponse> baseAllFind(){
        var baseEntityList = surveyService.baseAllFind();
        return surveyConverter.toBaseListResponse(baseEntityList);
    }

    @Transactional
    public SurveyReplyListResponse replyAllFind(Long id){
        var replyEntityList = surveyService.replyAllFind(id);
        var replyResponseList = surveyConverter.toReplyListResponse(replyEntityList);
        var response = new SurveyReplyListResponse(id, replyResponseList);
        return response;
    }

    @Transactional
    public SurveyListResponse deleteSurvey(Long id){
        var baseEntity = surveyService.baseDelete(id);
        return surveyConverter.toResponse(baseEntity);
    }
}
