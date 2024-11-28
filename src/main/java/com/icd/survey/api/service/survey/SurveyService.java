package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.CreateSurveyRequest;
import com.icd.survey.api.dto.survey.request.UpdateSurveyUpdateRequest;
import com.icd.survey.api.entity.survey.ItemResponseOption;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.entity.survey.dto.ItemResponseOptionDto;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.entity.survey.dto.SurveyItemDto;
import com.icd.survey.api.repository.survey.ResponseOptionRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
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
        if (Boolean.TRUE.equals(surveyQueryBusiness.isExsitedUserSurvey(request.getSurveyName(), request.getIpAddress()))) {
            throw new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND, "ip 당 설문조사 이름은 중복될 수 없습니다.");
        }

        /* 설문 조사 엔티티 save */
        Long surveySeq = surveyActionBusiness.saveSurvey(request.createSurveyDtoRequest()).getSurveySeq();

        /* 설문 조사 항목 save */
        surveyActionBusiness.saveSurveyItemList(request.getSurveyItemList(), surveySeq);
    }

    public void updateSurvey(UpdateSurveyUpdateRequest request) {

        /* 엔티티 확인 */
        Survey survey = surveyQueryBusiness.findById(request.getSurveySeq())
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        Long surveySeq = survey.getSurveySeq();

        surveyValidCheck(survey);

        survey.update(request.createSurveyDtoRequest());

        /* 기존의 설문조사 항목들 모두 disable 처리. */
        surveyActionBusiness.disableItemList(surveySeq);

        /* 설문 조사 항목 save */
        surveyActionBusiness.saveSurveyItemList(request.getSurveyItemList(), surveySeq);
    }

    // todo : 응답항목의 이름이나 응답 값을 기반으로 검색하는 부분 추가 필요
    public SurveyDto saerchSurvey(Long surveySeq) {

        return null;
    }

    public void surveyValidCheck(Survey survey) {
        if (survey.getIsDeleted() != null && Boolean.TRUE.equals(survey.getIsDeleted())) {
            throw new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND);
        }
    }
}
