package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.CreateSurveyRequest;
import com.icd.survey.api.dto.survey.request.SubmitSurveyRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.UpdateSurveyUpdateRequest;
import com.icd.survey.api.entity.survey.ItemAnswer;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.entity.survey.dto.ItemAnswerDto;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.entity.survey.dto.SurveyItemDto;
import com.icd.survey.api.service.survey.business.SurveyActionBusiness;
import com.icd.survey.api.service.survey.business.SurveyQueryBusiness;
import com.icd.survey.common.CommonUtils;
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyQueryBusiness surveyQueryBusiness;
    private final SurveyActionBusiness surveyActionBusiness;

    public void createSurvey(CreateSurveyRequest request) {

        request.setIpAddress(CommonUtils.getRequestIp());

        /* ip 당 설문조사 이름 체크 */
        if (Boolean.TRUE.equals(surveyQueryBusiness.isExistedUserSurvey(request.getSurveyName(), request.getIpAddress()))) {
            throw new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND, "ip 당 설문조사 이름은 중복될 수 없습니다.");
        }

        /* 설문 조사 엔티티 save */
        Long surveySeq = surveyActionBusiness.saveSurvey(request.createSurveyDtoRequest()).getSurveySeq();

        /* 설문 조사 항목 save */
        surveyActionBusiness.saveSurveyItemList(request.getSurveyItemList(), surveySeq);
    }

    public void updateSurvey(UpdateSurveyUpdateRequest request) {

        /* 엔티티 확인 */
        Survey survey = checkSurveyBySeq(request.getSurveySeq());

        survey.deletedCheck();

        Long surveySeq = survey.getSurveySeq();

        survey.update(request.createSurveyDtoRequest());

        /* 기존의 설문조사 항목들 모두 disable 처리. */
        surveyActionBusiness.updateSurveyItemAsDisabled(surveySeq);

        /* 설문 조사 항목 save */
        surveyActionBusiness.saveSurveyItemList(request.getSurveyItemList(), surveySeq);
    }

    public void submitSurvey(SubmitSurveyRequest request) {

        Survey survey = checkSurveyBySeq(request.getSurveySeq());

        if (Boolean.FALSE.equals(survey.getIpAddress().equals(CommonUtils.getRequestIp()))) {
            throw new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND);
        }

        survey.deletedCheck();

        List<SurveyItem> itemList = surveyQueryBusiness.findItemAllBySurveySeq(survey.getSurveySeq())
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        // 필수 항목 값 queue
        Set<Long> essentialItemSeqSet = itemList
                .stream()
                .filter(SurveyItem::getIsEssential)
                .map(SurveyItem::getItemSeq)
                .collect(Collectors.toSet());

        List<SurveyItemRequest> itemRequestList = request.getSurveyItemList();

        itemRequestList.forEach(x -> {
            essentialItemSeqSet.remove(x.getItemSeq());
        });

        if (Boolean.FALSE.equals(essentialItemSeqSet.isEmpty())) {
            throw new ApiException(ExceptionResponseType.ILLEGAL_ARGUMENT, "필수 항목 값을 입력 해 주세요");
        }

        request.getSurveyItemList()
                .forEach(surveyActionBusiness::answerSurveyItem);

    }

    public SurveyDto getSurveyAnswer(Long surveySeq) {

        Survey survey = surveyQueryBusiness.findSurveyById(surveySeq)
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        survey.deletedCheck();

        SurveyDto result = survey.of();

        List<SurveyItemDto> itemDtoList = new ArrayList<>();

        List<SurveyItem> itemList = surveyQueryBusiness.findItemAllBySurveySeq(survey.getSurveySeq())
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        itemList.forEach(x -> {
            List<ItemAnswerDto> answerDtoList = new ArrayList<>();

            List<ItemAnswer> answerList = surveyQueryBusiness.findItemAnswerList(x.getItemSeq())
                    .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

            answerList.forEach(y -> answerDtoList.add(y.of()));

            SurveyItemDto itemDto = x.of();
            itemDto.setItemAnswerList(answerDtoList);
            itemDtoList.add(itemDto);
        });

        result.setSurveyItemList(itemDtoList);

        return result;
    }

    public Survey checkSurveyBySeq(Long surveySeq){
        return surveyQueryBusiness.findSurveyById(surveySeq)
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));
    }
}
