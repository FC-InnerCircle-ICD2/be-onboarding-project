package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.SurveyRequest;
import com.icd.survey.api.entity.dto.ItemResponseOptionDto;
import com.icd.survey.api.entity.dto.SurveyItemDto;
import com.icd.survey.api.entity.survey.ItemResponseOption;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.repository.survey.ResponseOptionRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
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

    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final ResponseOptionRepository responseOptionRepository;

    public void createSurvey(SurveyRequest request) {
        request.validationCheck();

        request.setIpAddress(CommonUtils.getRequestIp());

        /* ip 당 설문조사 이름 체크 */
        Survey existingSurvey = surveyRepository.findBySurveyNameAndIpAddress(request.getSurveyName(), request.getIpAddress())
                .orElse(null);

        if (existingSurvey != null) {
            throw new ApiException(ExceptionResponseType.ALREADY_EXISTS, "ip 당 설문 조사 이름은 중복될 수 없습니다.");
        }

        Survey survey = surveyRepository.save(Survey.createSurveyRequest(request.createSurveyDtoRequest()));

        List<SurveyItem> surveyItemList = new ArrayList<>();

        for (SurveyItemRequest itemRequest : request.getSurveyItemList()) {

            itemRequest.validationCheck();

            SurveyItemDto itemDto = itemRequest.createSurveyItemDtoRequest();
            SurveyItem surveyItem = SurveyItem.createSurveyItemRequest(itemDto);
            surveyItem.setSurveySeq(survey.getSurveySeq());
            /* 선택 형 문항의 경우 option list 처리 */
            if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {
                List<ItemResponseOption> responseOptionList = new ArrayList<>();

                for (ItemOptionRequest optionRequest : itemRequest.getOptionList()) {
                    optionRequest.validationCheck();

                    ItemResponseOptionDto optionDto = optionRequest.createItemREsponseOptionDto();
                    responseOptionList.add(ItemResponseOption.createItemResponseOptionRequest(optionDto));
                }
                surveyItem.saveResponseOptionList(responseOptionList);
                surveyItemRepository.save(surveyItem);
            }
            surveyItemList.add(surveyItem);
        }
        survey.saveSurveyItemList(surveyItemList);
    }

}
