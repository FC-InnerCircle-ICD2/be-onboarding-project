package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.SurveyRequest;
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
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class SurveyService {
    private static final String TEST = "TEST";
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

            if (itemRequest.isChoiceType()) {

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

}
