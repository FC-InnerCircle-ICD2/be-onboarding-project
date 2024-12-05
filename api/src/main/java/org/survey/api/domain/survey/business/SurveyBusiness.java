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
        var newBaseEntity = surveyService.baseRegister(baseEntity);

        //설문조사 항목 등록
        List<SurveyItemResponse> items = new ArrayList<>();
        for(SurveyItemRequest item : request.getItems()){
            var itemEntity = surveyConverter.toEntity(item, newBaseEntity.getId());
            var newItemEntity = surveyService.itemRegister(itemEntity);

            //항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 선택 리스트 등록
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
        var baseEntity = surveyService.baseFindById(id);

        //설문조사 항목 List Select
        var itemEntityList = surveyService.itemAllFind(baseEntity.getId());

        //항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 선택 리스트 Select
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
        var newBaseEntity = surveyService.baseUpdate(request.getId(), baseEntity);

        //설문조사 항목 수정
        List<SurveyItemResponse> items = new ArrayList<>();
        HashSet<Long> itemIdList = new HashSet<>();
        for(SurveyItemRequest item : request.getItems()){
            var itemEntity = surveyConverter.toEntity(item, newBaseEntity.getId());
            var newItemEntity = surveyService.itemUpdate(itemEntity, newBaseEntity.getId());

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
                    var newSelectListEntity = surveyService.selectListUpdate(
                            selectListEntity,
                            newBaseEntity.getId(),
                            newItemEntity.getId());
                    var optionResponse = surveyConverter.toResponse(newSelectListEntity);
                    selectOptionResponseList.add(optionResponse);
                    optionIdList.add(optionResponse.getId());
                }

                //불필요 선택 리스트 삭제
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

            //설문조사 항목 Response 변환
            var itemResponse = surveyConverter.toResponse(newItemEntity, selectOptionResponseList);
            items.add(itemResponse);
            itemIdList.add(itemResponse.getId());
        }

        //불필요 설문조사 항목 삭제
        List<SurveyItemEntity> currentItemList = surveyService.itemAllFind(newBaseEntity.getId());
        for(SurveyItemEntity currentItem : currentItemList){
            if(!itemIdList.contains(currentItem.getId())){
                surveyService.itemDelete(
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
        SurveyBaseEntity surveyBaseEntity = surveyService.baseFindById(surveyReplyListRequest.getId());

        //설문조사 항목 리스트 Select
        List<SurveyReplyResponse> replies = new ArrayList<>();
        for(SurveyReplyRequest surveyReplyRequest : surveyReplyListRequest.getReply()){
            var surveyReplyEntity = surveyConverter.toEntity(surveyReplyRequest, surveyReplyListRequest.getId());
            var surveyItem = surveyService.itemFindById(surveyReplyEntity.getItemId(), surveyReplyListRequest.getId());

            //항목 입력 형태가 [단일선택리스트], [다중선택리스트]의 경우 유효한 후보 선택 여부 판별
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

            //항목 입력 형태가 [다중선택리스트]가 아닌 경우 답변 중복 여부 판별
            if(!(surveyItem.getInputType() == ItemInputType.MULTI_SELECT_LIST)){
                List<SurveyReplyEntity> surveyReplyEntityList = surveyService.replyMultiFind(
                        surveyReplyListRequest.getId(), surveyReplyEntity.getItemId());
                if(surveyReplyEntityList.size() > 0){
                    throw new ApiException(SelectErrorCode.DUPLICATE_REQUEST, "This is a duplicate request");
                }
            }

            //설문조사 응답 등록
            var newSurveyReplyEntity = surveyService.replyRegister(surveyReplyEntity);
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
        var baseEntityList = surveyService.baseAllFind();
        return surveyConverter.toBaseListResponse(baseEntityList);
    }

    /**
     * 설문조사 응답 조회
     */
    @Transactional
    public SurveyReplyListResponse replyAllFind(Long id){
        var replyEntityList = surveyService.replyAllFind(id);
        var replyResponseList = surveyConverter.toReplyListResponse(replyEntityList);
        var response = new SurveyReplyListResponse(id, replyResponseList);
        return response;
    }

    /**
     * 설문조사 삭제
     */
    @Transactional
    public SurveyListResponse deleteSurvey(Long id){
        var baseEntity = surveyService.baseDelete(id);
        return surveyConverter.toResponse(baseEntity);
    }

    /**
     * 설문조사 응답 조회(항목 이름, 응답 값)
     */
    @Transactional
    public SurveyBaseResponse replyFindByItemAndContent(SurveySearchRequest surveySearchRequest){
        //설문조사 폼 Select
        var baseEntity = surveyService.baseFindById(surveySearchRequest.getSurveyId());

        if(surveySearchRequest.getItemName() != null){
            var surveyItemEntity = surveyService.itemFindByName(
                    surveySearchRequest.getSurveyId(), surveySearchRequest.getItemName());
            List<SurveyItemResponse> itemResponseList = new ArrayList<>();
            List<SelectOptionResponse> selectOptionList = new ArrayList<>();
            if(surveyItemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                    || surveyItemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                var selectListEntityList = surveyService.selectListAllFind(
                        baseEntity.getId(),
                        surveyItemEntity.getId());
                for(SelectListEntity selectListEntity : selectListEntityList){
                    selectOptionList.add(surveyConverter.toResponse(selectListEntity));
                }
            }
            var itemResponse = surveyConverter.toResponse(surveyItemEntity, selectOptionList);
            var replyEntityList = surveyService.replyMultiFind(
                    baseEntity.getId(),
                    surveyItemEntity.getId());
            itemResponse.setSurveyReply(surveyConverter.toReplyListResponse(replyEntityList));
            itemResponseList.add(itemResponse);
            var response = surveyConverter.toResponse(baseEntity, itemResponseList);
            return response;
        }
        else if(surveySearchRequest.getReplyContent() != null) {
            var surveyReplyList = surveyService.replyFindByContent(
                    surveySearchRequest.getSurveyId(), surveySearchRequest.getReplyContent());
            List<SurveyItemResponse> itemResponseList = new ArrayList<>();
            for(SurveyReplyEntity reply : surveyReplyList){
                var surveyItemEntity = surveyService.itemFindById(
                        reply.getItemId(), surveySearchRequest.getSurveyId());
                List<SelectOptionResponse> selectOptionList = new ArrayList<>();
                if(surveyItemEntity.getInputType() == ItemInputType.SINGLE_SELECT_LIST
                        || surveyItemEntity.getInputType() == ItemInputType.MULTI_SELECT_LIST){
                    var selectListEntityList = surveyService.selectListAllFind(
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
