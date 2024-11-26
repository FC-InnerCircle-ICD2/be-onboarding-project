package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.SurveyRequest;
import com.icd.survey.api.dto.survey.request.SurveyUpdateRequest;
import com.icd.survey.api.entity.dto.SurveyDto;
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

        // 유효성 검사
        request.validationCheck();

        Survey saerchSurvey = surveyRepository.findBySurveyNameAndIpAddress(request.getSurveyName(), CommonUtils.getRequestIp())
                .orElse(null);

        if (saerchSurvey != null) {
            throw new ApiException(ExceptionResponseType.VALIDATION_EXCEPTION, "동일 ip당 같은 이름의 설문조사는 만들 수 없습니다.");
        }

        Survey surveyEntity = surveyRepository.save(request.toEntity());

        // 항목 request 객체
        List<SurveyItemRequest> itemRequestList = request.getSurveyItemList();

        for (SurveyItemRequest itemRequest : itemRequestList) {
            itemRequest.validationCheck();

            // survey item insert request entity
            SurveyItem surveyItemRequest = itemRequest.toEntity();
            surveyItemRequest.setSurvey(surveyEntity);

            SurveyItem surveyItem = surveyItemRepository.save(surveyItemRequest);

            if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {

                List<ItemOptionRequest> optionRequestList = itemRequest.getOptionList();

                for (ItemOptionRequest optionRequest : optionRequestList) {
                    optionRequest.validationCheck();

                    // item response option insert request entity
                    ItemResponseOption itemResponseRequestOption = optionRequest.toEntity();
                    itemResponseRequestOption.setSurveyItem(surveyItem);

                    responseOptionRepository.save(itemResponseRequestOption);
                }
            }

        }
    }


    public SurveyDto modifySurvey(SurveyUpdateRequest request) {

        request.validationCheck();

        Survey originSurvey = surveyRepository.findById(request.getSurveySeq())
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND, "존재하지 않는 설문조사 번호 입니다."));

        originSurvey.update(request);

        // 기존의 설문조사 항목들과, 항목 선택 옵션들 delete
        deleteAllSurveyItemAndResponseOptions(originSurvey);

        for (SurveyItemRequest itemRequest : request.getSurveyItemList()) {
            itemRequest.validationCheck();

            SurveyItem surveyItem = itemRequest.toEntity();
            surveyItem.setSurvey(originSurvey);
            surveyItemRepository.save(surveyItem);

            if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {
                for (ItemOptionRequest optionRequest : itemRequest.getOptionList()) {
                    optionRequest.validationCheck();

                    ItemResponseOption option = optionRequest.toEntity();
                    option.setSurveyItem(surveyItem);
                    responseOptionRepository.save(option);
                }
            }
        }

        // Todo : 새로 등록된 항목들과 Survey 를 가져와야 함.

        return null;
    }

    public void deleteAllSurveyItemAndResponseOptions(Survey survey) {
        List<SurveyItem> originSurveyItemList = surveyItemRepository.findBySurveyAndIsDeletedTrue(survey)
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND, "설문 조사 항목이 존재하지 않습니다."));

        surveyItemRepository.makeAllAsDeletedBySurveySeq(survey.getSurveySeq());

        originSurveyItemList.forEach(x -> responseOptionRepository.makeAllAsDeletedBySurveyItemSeq(x.getItemSeq()));
    }

}
