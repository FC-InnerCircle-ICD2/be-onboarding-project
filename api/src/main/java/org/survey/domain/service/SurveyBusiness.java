package org.survey.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.survey.common.annotation.Business;
import org.survey.common.error.SelectErrorCode;
import org.survey.common.exception.ApiException;
import org.survey.domain.controller.model.*;
import org.survey.domain.converter.SurveyConverter;
import org.survey.domain.service.selectlist.SelectListEntity;
import org.survey.domain.service.selectlist.SelectListService;
import org.survey.domain.service.surveyanswer.SurveyAnswerService;
import org.survey.domain.service.surveyanswer.SurveyReplyEntity;
import org.survey.domain.service.surveybase.SurveyBaseEntity;
import org.survey.domain.service.surveybase.SurveyBaseService;
import org.survey.domain.service.surveyitem.ItemInputType;
import org.survey.domain.service.surveyitem.SurveyItemEntity;
import org.survey.domain.service.surveyitem.SurveyItemService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Business
public class SurveyBusiness {

    private final SurveyBaseService surveyBaseService;
    private final SurveyItemService surveyItemService;
    private final SelectListService selectListService;
    private final SurveyAnswerService surveyAnswerService;
    private final SurveyConverter surveyConverter;

    /**
     * 설문조사 생성
     * 1. 설문조사 폼 등록
     * 2. 설문조사 항목 등록
     * 3. 항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 선택 리스트 등록
     * 4. 설문조사 등록 결과 return
     */
    @Transactional
    public SurveyBaseResponse register(SurveyBaseRequest request){
        //설문조사 폼 등록
        var baseEntity = surveyConverter.toEntity(request);
        var newBaseEntity = surveyBaseService.baseRegister(baseEntity);

        //설문조사 항목 등록
        List<SurveyItemResponse> items = new ArrayList<>();
        for(SurveyItemRequest item : request.getItems()){
            var itemEntity = surveyConverter.toEntity(item, newBaseEntity.getId());
            var newItemEntity = surveyItemService.itemRegister(itemEntity);

            //항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 선택 리스트 등록
            List<SelectOptionResponse> selectOptionResponseList = new ArrayList<>();
            if(newItemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || newItemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                for(SelectOptionRequest option : item.getSelectOptions()){
                    var selectListEntity = surveyConverter.toEntity(
                            option,
                            newBaseEntity.getId(),
                            newItemEntity.getId());
                    var newSelectListEntity = selectListService.selectListRegister(selectListEntity);
                    selectOptionResponseList.add(surveyConverter.toResponse(newSelectListEntity));
                }
            }

            //설문조사 항목 Response 변환
            var itemResponse = surveyConverter.toResponse(newItemEntity, selectOptionResponseList);
            items.add(itemResponse);
        }
        //설문조사 등록 결과 return
        var response = surveyConverter.toResponse(newBaseEntity, items);
        return response;
    }

    /**
     * 식별자를 통한 설문조사 검색
     * 1. 설문조사 폼 Select
     * 2. 설문조사 항목 List Select
     * 3. 항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 선택 리스트 Select
     * 4. 설문조사 검색 결과 return
     */
    @Transactional
    public SurveyBaseResponse find(Long id){
        //설문조사 폼 Select
        var baseEntity = surveyBaseService.baseFindById(id);

        //설문조사 항목 List Select
        var itemEntityList = surveyItemService.itemAllFind(baseEntity.getId());

        //항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 선택 리스트 Select
        List<SurveyItemResponse> itemResponseList = new ArrayList<>();
        for(SurveyItemEntity itemEntity : itemEntityList){
            List<SelectOptionResponse> selectOptionList = new ArrayList<>();
            if(itemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || itemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                var selectListEntityList = selectListService.selectListAllFind(
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
        
        //설문조사 검색 결과 return
        var response = surveyConverter.toResponse(baseEntity, itemResponseList);
        return response;
    }

    /**
     * 설문조사 수정
     * 1. 설문조사 폼 수정
     * 2. 설문조사 항목 수정
     * 3. 항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 선택 리스트 수정
     * 4. 항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 불필요 선택 리스트 삭제
     * 5. 불필요 설문조사 항목 삭제
     * 6. 설문조사 수정 결과 return
     */
    @Transactional
    public SurveyBaseResponse updateAll(
            SurveyBaseRequest request
    ){
        //설문조사 폼 수정
        var baseEntity = surveyConverter.toEntity(request);
        var newBaseEntity = surveyBaseService.baseUpdate(request.getId(), baseEntity);

        //설문조사 항목 수정
        List<SurveyItemResponse> items = new ArrayList<>();
        HashSet<Long> itemIdList = new HashSet<>();
        for(SurveyItemRequest item : request.getItems()){
            var itemEntity = surveyConverter.toEntity(item, newBaseEntity.getId());
            var newItemEntity = surveyItemService.itemUpdate(itemEntity, newBaseEntity.getId());

            //항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 선택 리스트 수정
            List<SelectOptionResponse> selectOptionResponseList = new ArrayList<>();
            if(newItemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || newItemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                HashSet<Long> optionIdList = new HashSet<>();
                for(SelectOptionRequest option : item.getSelectOptions()){
                    var selectListEntity = surveyConverter.toEntity(
                            option,
                            newBaseEntity.getId(),
                            newItemEntity.getId());
                    var newSelectListEntity = selectListService.selectListUpdate(
                            selectListEntity,
                            newBaseEntity.getId(),
                            newItemEntity.getId());
                    var optionResponse = surveyConverter.toResponse(newSelectListEntity);
                    selectOptionResponseList.add(optionResponse);
                    optionIdList.add(optionResponse.getId());
                }

                //불필요 선택 리스트 삭제
                List<SelectListEntity> currentSelectList = selectListService.selectListAllFind(
                        newBaseEntity.getId(),
                        newItemEntity.getId());
                for(SelectListEntity currentOption : currentSelectList){
                    if(!optionIdList.contains(currentOption.getId())){
                        selectListService.selectListDelete(
                                currentOption.getId(),
                                currentOption.getSurveyId(),
                                currentOption.getItemId());
                    }
                }
            }

            //설문조사 항목 Response 변환
            var itemResponse = surveyConverter.toResponse(newItemEntity, selectOptionResponseList);
            items.add(itemResponse);
            itemIdList.add(itemResponse.getId());
        }

        //불필요 설문조사 항목 삭제
        List<SurveyItemEntity> currentItemList = surveyItemService.itemAllFind(newBaseEntity.getId());
        for(SurveyItemEntity currentItem : currentItemList){
            if(!itemIdList.contains(currentItem.getId())){
                surveyItemService.itemDelete(
                        currentItem.getId(),
                        currentItem.getSurveyId());
            }
        }
        
        //설문조사 수정 결과 return
        var response = surveyConverter.toResponse(newBaseEntity, items);
        return response;
    }

    /**
     * 설문조사 응답 제출
     * 1. 유효한 설문조사 폼인지 판별
     * 2. 설문조사 항목 리스트 Select
     * 3. 항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 유효한 후보 선택 여부 판별
     * 4. 항목 입력 형태가 [다중선택리스트]가 아닌 경우 답변 중복 여부 판별
     * 5. 설문조사 응답 결과 return
     */
    @Transactional
    public SurveyReplyListResponse reply(SurveyReplyListRequest surveyReplyListRequest){
        //유효한 설문조사 폼인지 판별 
        SurveyBaseEntity surveyBaseEntity = surveyBaseService.baseFindById(surveyReplyListRequest.getId());

        //설문조사 항목 리스트 Select
        List<SurveyReplyResponse> replies = new ArrayList<>();
        for(SurveyReplyRequest surveyReplyRequest : surveyReplyListRequest.getReply()){
            var surveyReplyEntity = surveyConverter.toEntity(surveyReplyRequest, surveyReplyListRequest.getId());
            var surveyItem = surveyItemService.itemFindById(surveyReplyEntity.getItemId(), surveyReplyListRequest.getId());

            //항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 유효한 후보 선택 여부 판별
            if(surveyItem.getInputType() == ItemInputType.SINGLE_SELECT_LIST
            || surveyItem.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                List<SelectListEntity> selectListEntityList = selectListService.selectListAllFind(
                        surveyReplyListRequest.getId(), surveyReplyEntity.getItemId());
                Set<String> contentHashSet = new HashSet<>();
                for(SelectListEntity options : selectListEntityList){
                    contentHashSet.add(options.getContent());
                }
                if(!contentHashSet.contains(surveyReplyRequest.getContent())){
                    throw new ApiException(SelectErrorCode.NON_EXIST_REQUEST, "The request does not exist in the selection list.");
                }
            }

            //항목 입력 형태가 [다중선택리스트]가 아닌 경우 답변 중복 여부 판별
            if(!(surveyItem.getInputType() == ItemInputType.MULTI_SELECT_LIST)){
                List<SurveyReplyEntity> surveyReplyEntityList = surveyAnswerService.replyMultiFind(
                        surveyReplyListRequest.getId(), surveyReplyEntity.getItemId());
                if(surveyReplyEntityList.size() > 0){
                    throw new ApiException(SelectErrorCode.DUPLICATE_REQUEST, "This is a duplicate request");
                }
            }

            //설문조사 응답 등록
            var newSurveyReplyEntity = surveyAnswerService.replyRegister(surveyReplyEntity);
            var surveyReplyResponse = surveyConverter.toResponse(newSurveyReplyEntity);
            replies.add(surveyReplyResponse);
        }

        //설문조사 응답 결과 return
        var response = new SurveyReplyListResponse(surveyReplyListRequest.getId(), replies);
        return response;
    }

    /**
     * 모든 설문조사 List Select
     */
    @Transactional
    public List<SurveyListResponse> baseAllFind(){
        var baseEntityList = surveyBaseService.baseAllFind();
        return surveyConverter.toBaseListResponse(baseEntityList);
    }

    /**
     * 설문조사 응답 조회
     */
    @Transactional
    public SurveyReplyListResponse replyAllFind(Long id){
        var replyEntityList = surveyAnswerService.replyAllFind(id);
        var replyResponseList = surveyConverter.toReplyListResponse(replyEntityList);
        var response = new SurveyReplyListResponse(id, replyResponseList);
        return response;
    }

    /**
     * 설문조사 삭제
     */
    @Transactional
    public SurveyListResponse deleteSurvey(Long id){
        var baseEntity = surveyBaseService.baseDelete(id);
        return surveyConverter.toResponse(baseEntity);
    }

    /**
     * 설문조사 응답 조회(항목 이름, 응답 값)
     */
    @Transactional
    public SurveyBaseResponse replyFindByItemAndContent(SurveySearchRequest surveySearchRequest){
        //설문조사 폼 Select
        var baseEntity = surveyBaseService.baseFindById(surveySearchRequest.getSurveyId());

        if(surveySearchRequest.getItemName() != null){
            var surveyItemEntity = surveyItemService.itemFindByName(
                    surveySearchRequest.getSurveyId(), surveySearchRequest.getItemName());
            List<SurveyItemResponse> itemResponseList = new ArrayList<>();
            List<SelectOptionResponse> selectOptionList = new ArrayList<>();
            if(surveyItemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || surveyItemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                var selectListEntityList = selectListService.selectListAllFind(
                        baseEntity.getId(),
                        surveyItemEntity.getId());
                for(SelectListEntity selectListEntity : selectListEntityList){
                    selectOptionList.add(surveyConverter.toResponse(selectListEntity));
                }
            }
            var itemResponse = surveyConverter.toResponse(surveyItemEntity, selectOptionList);
            var replyEntityList = surveyAnswerService.replyMultiFind(
                    baseEntity.getId(),
                    surveyItemEntity.getId());
            itemResponse.setSurveyReply(surveyConverter.toReplyListResponse(replyEntityList));
            itemResponseList.add(itemResponse);
            var response = surveyConverter.toResponse(baseEntity, itemResponseList);
            return response;
        }
        else if(surveySearchRequest.getReplyContent() != null) {
            var surveyReplyList = surveyAnswerService.replyFindByContent(
                    surveySearchRequest.getSurveyId(), surveySearchRequest.getReplyContent());
            List<SurveyItemResponse> itemResponseList = new ArrayList<>();
            for(SurveyReplyEntity reply : surveyReplyList){
                var surveyItemEntity = surveyItemService.itemFindById(
                        reply.getItemId(), surveySearchRequest.getSurveyId());
                List<SelectOptionResponse> selectOptionList = new ArrayList<>();
                if(surveyItemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                        || surveyItemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                    var selectListEntityList = selectListService.selectListAllFind(
                            baseEntity.getId(),
                            surveyItemEntity.getId());
                    for(SelectListEntity selectListEntity : selectListEntityList){
                        selectOptionList.add(surveyConverter.toResponse(selectListEntity));
                    }
                }
                var itemResponse = surveyConverter.toResponse(surveyItemEntity, selectOptionList);
                List<SurveyReplyEntity> replyEntityList = new ArrayList<>();
                replyEntityList.add(reply);
                itemResponse.setSurveyReply(surveyConverter.toReplyListResponse(replyEntityList));
                itemResponseList.add(itemResponse);
            }
            var response = surveyConverter.toResponse(baseEntity, itemResponseList);
            return response;
        }
        var response = this.find(surveySearchRequest.getSurveyId());
        return response;
    }
}
