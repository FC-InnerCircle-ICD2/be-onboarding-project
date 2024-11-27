package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.CreateSurveyRequest;
import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.UpdateSurveyUpdateRequest;
import com.icd.survey.api.entity.dto.ItemResponseOptionDto;
import com.icd.survey.api.entity.dto.SurveyDto;
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
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final ResponseOptionRepository responseOptionRepository;

    public void createSurvey(CreateSurveyRequest request) {

        request.setIpAddress(CommonUtils.getRequestIp());

        /* ip 당 설문조사 이름 체크 */
        Survey existingSurvey = surveyRepository.findBySurveyNameAndIpAddressAndIsDeletedFalse(request.getSurveyName(), request.getIpAddress())
                .orElse(null);

        if (existingSurvey != null) {
            throw new ApiException(ExceptionResponseType.ALREADY_EXISTS, "ip 당 설문 조사 이름은 중복될 수 없습니다.");
        }

        /* 설문 조사 엔티티 save */
        Survey survey = surveyRepository.save(Survey.createSurveyRequest(request.createSurveyDtoRequest()));

        /* 설문 조사 항목 save */
        for (SurveyItemRequest itemRequest : request.getSurveyItemList()) {

            itemRequest.validationCheck();

            SurveyItemDto itemDto = itemRequest.createSurveyItemDtoRequest();
            SurveyItem surveyItem = SurveyItem.createSurveyItemRequest(itemDto);
            surveyItem.surveyKeySet(survey.getSurveySeq());
            surveyItem = surveyItemRepository.save(surveyItem);

            /* 선택형 문항의 경우 option list 처리 */
            if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {

                /* 선택형 문항의 선택 옵션 save */
                for (ItemOptionRequest optionRequest : itemRequest.getOptionList()) {
                    optionRequest.validationCheck();

                    ItemResponseOptionDto optionDto = optionRequest.createItemREsponseOptionDto();
                    ItemResponseOption option = ItemResponseOption.createItemResponseOptionRequest(optionDto);
                    option.itemKeySet(surveyItem.getItemSeq());
                    responseOptionRepository.save(option);
                }
            }
        }
    }

    public void updateSurvey(UpdateSurveyUpdateRequest request) {

        /* 엔티티 확인 */
        Survey survey = surveyRepository.findById(request.getSurveySeq()).orElse(null);

        surveyValidCheck(survey);

        survey.update(request.createSurveyDtoRequest());

        /* 기존의 설문조사 항목들 모두 disable 처리. */
        Optional<List<SurveyItem>> optionalSurveyItemList = surveyItemRepository.findAllBySurveySeq(survey.getSurveySeq());

        if (Boolean.TRUE.equals(optionalSurveyItemList.isPresent())) {
            List<SurveyItem> surveyItemList = optionalSurveyItemList.get();
            surveyItemList.forEach(SurveyItem::disable);
        }

        /* 새로운 설문조사 항목들 save */
        for (SurveyItemRequest itemRequest : request.getSurveyItemList()) {
            itemRequest.validationCheck();

            SurveyItem newItem = SurveyItem.createSurveyItemRequest(itemRequest.createSurveyItemDtoRequest());
            newItem.surveyKeySet(survey.getSurveySeq());

            newItem = surveyItemRepository.save(newItem);

            /* 선택형 문항의 경우 option list 처리 */
            if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {
                for (ItemOptionRequest optionRequest : itemRequest.getOptionList()) {
                    optionRequest.validationCheck();

                    ItemResponseOption option = ItemResponseOption.createItemResponseOptionRequest(optionRequest.createItemREsponseOptionDto());
                    option.itemKeySet(newItem.getItemSeq());

                    responseOptionRepository.save(option);
                }
            }
        }
    }

    // todo : 응답항목의 이름이나 응답 값을 기반으로 검색하는 부분 추가 필요
    public SurveyDto getSurvey(Long surveySeq) {

        Survey survey = surveyRepository.findById(surveySeq)
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        surveyValidCheck(survey);

        SurveyDto surveyDto = survey.of();

        List<SurveyItemDto> surveyItemDtoList = new ArrayList<>();

        List<SurveyItem> surveyItemList = surveyItemRepository.findAllBySurveySeq(surveySeq)
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        for (SurveyItem surveyItem : surveyItemList) {
            List<ItemResponseOptionDto> optionList = new ArrayList<>();
            SurveyItemDto itemDto = surveyItem.of();

            List<ItemResponseOption> responseList = responseOptionRepository.findByItemSeq(surveyItem.getItemSeq())
                    .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

            for (ItemResponseOption option : responseList) {
                ItemResponseOptionDto optionDto = option.of();
                optionList.add(optionDto);
            }
            itemDto.setResponseOptionList(optionList);
            surveyItemDtoList.add(itemDto);

        }
        surveyDto.setSurveyItemList(surveyItemDtoList);
        return surveyDto;
    }

    public void surveyValidCheck(Survey survey) {
        if (survey == null
                || Boolean.TRUE.equals(survey.getIsDeleted())
                || Boolean.FALSE.equals(survey.getIpAddress().equals(CommonUtils.getRequestIp()))) {
            throw new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND);
        }
    }
}
