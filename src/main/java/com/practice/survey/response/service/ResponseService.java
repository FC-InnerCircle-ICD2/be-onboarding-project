package com.practice.survey.response.service;

import com.practice.survey.common.response.ResponseTemplate;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.response.model.dto.ResponseSaveDto;
import com.practice.survey.response.model.dto.ResponseSaveRequestDto;
import com.practice.survey.response.model.entity.Response;
import com.practice.survey.response.repository.ResponseRepository;
import com.practice.survey.responseItem.model.dto.ResponseItemSaveDto;
import com.practice.survey.responseItem.model.dto.ResponseItemSaveRequestDto;
import com.practice.survey.responseItem.repository.ResponseItemRepository;
import com.practice.survey.surveyItem.model.dto.SurveyItemReadDto;
import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyItem.repository.SurveyItemRepository;
import com.practice.survey.surveyItemOption.repository.SurveyItemOptionRepository;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import com.practice.survey.surveyVersion.repository.SurveyVersionRepository;
import com.practice.survey.surveymngt.model.entity.Survey;
import com.practice.survey.surveymngt.repository.SurveyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final ResponseItemRepository responseItemRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyVersionRepository surveyVersionRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final SurveyItemOptionRepository surveyItemOptionRepository;

    
    public ResponseTemplate<StatusEnum> createResponse(ResponseSaveRequestDto responseSaveRequestDto) {

        // 설문조사 아이디 기반 설문조사 찾기
        Survey survey = surveyRepository.findBySurveyId(responseSaveRequestDto.getSurveyId());

        // 설문조사 아이디와 버전 넘버로 설문조사 버전 찾기
        int surveyVersionNumber = responseSaveRequestDto.getSurveyVersionNumber();
        SurveyVersion surveyVersion =
                surveyVersionRepository.findBySurvey_SurveyIdAndVersionNumber(
                        responseSaveRequestDto.getSurveyId(), surveyVersionNumber);

        // 설문조사 버전 기반 설문조사 항목 찾기
        List<SurveyItem> surveyItems = surveyItemRepository.findByVersion(surveyVersion);
        List<SurveyItemReadDto> surveyItemReadDtos =surveyItems.stream()
                .map(SurveyItemReadDto::fromEntity)
                .toList();

        List<ResponseItemSaveRequestDto> responseItemSaveRequestDtos = responseSaveRequestDto.getResponseItems();

        // 설문조사 항목별 응답 유효성 검사
        for(int i=0;i<surveyItemReadDtos.size();i++) {
            SurveyItem surveyItem = surveyItems.get(i);
            SurveyItemReadDto surveyItemReadDto = surveyItemReadDtos.get(i);
            ResponseItemSaveRequestDto responseItemSaveRequestDto = responseItemSaveRequestDtos.get(i);
            List<String> responseValueList = responseItemSaveRequestDto.getResponseValue();
            
            String inputType = String.valueOf(surveyItemReadDto.getInputType());
            boolean required = surveyItemReadDto.isRequired();

//            필수 필드 응답 여부 검사 : 모든 필수 항목에 대응하는 응답이 있는지 확인합니다.
            // 필수 필드가 비어있을 경우 에러
            if(required && responseValueList.isEmpty()) {
                return new ResponseTemplate<StatusEnum>().serverError(StatusEnum.REQUIRED_FIELD_MISSING);
            }

            // 입력 유형(inputType) 검사 : 응답 값이 설문 항목의 예상 입력 유형과 일치하는지 확인합니다.
//            checkInputType(surveyItemReadDto, responseItemSaveRequestDto); // 이후 모듈화
            if(!required) continue; // 필수 응답 항목이 아닌 경우, 아래 유효성 검사를 생략합니다.
            // MULTIPLE_CHOICE : 2개 이상의 응답값이 있을 경우 통과. 2개 미만일 경우 에러
            if(inputType.equals("MULTIPLE_CHOICE")) {
                if(responseValueList.size() < 2) {
                    return new ResponseTemplate<StatusEnum>().serverError(StatusEnum.MULTIPLE_CHOICE_TOO_SHORT);
                }
            }
            // 다중 선택이 아닌데 응답값이 1개가 아닐 경우 에러
            else{
                if(responseValueList.size() != 1) {
                    // SINGLE_CHOICE : 1개의 응답값만 있을 경우 통과. 응답값이 1개가 아닐 경우 에러
                    if(inputType.equals("SINGLE_CHOICE")) {
                        return new ResponseTemplate<StatusEnum>().serverError(StatusEnum.SINGLE_CHOICE_MISMATCH);
                    }
                    else{
                        return new ResponseTemplate<StatusEnum>().serverError(StatusEnum.INPUT_TYPE_MISMATCH);
                    }
                }
            }
            // SHORT_TEXT : 20자 미만일 경우 통과. 20자 이상일 경우 에러
            if(inputType.equals("SHORT_TEXT")) {
                String responseValue = responseValueList.get(0);
                if(responseValue.length() >= 20) {
                    return new ResponseTemplate<StatusEnum>().serverError(StatusEnum.SHORT_TEXT_TOO_LONG);
                }
            }
            // LONG_TEXT : 20자 이상 200자 이하는 통과. 20자 미만이거나 200자 초과일 경우 에러
            if(inputType.equals("LONG_TEXT")) {
                String responseValue = responseValueList.get(0);
                if(responseValue.length() < 20 || responseValue.length() > 200) {
                    return new ResponseTemplate<StatusEnum>().serverError(StatusEnum.LONG_TEXT_LENGTH_MISMATCH);
                }
            }

            // 옵션 유효성 검사 : 설문 항목에 미리 정의된 옵션이 있는 경우, 응답 값이 그 중 하나와 일치하는지 확인합니다.
            // 입력 유형(inputType)이 SINGLE_CHOICE 또는 MULTIPLE_CHOICE인 경우에만 적용됩니다.
            if(inputType.equals("SINGLE_CHOICE") || inputType.equals("MULTIPLE_CHOICE")) {
                List<String> optionTextList = surveyItemOptionRepository.findOptionTextBySurveyItem(surveyItem);
//                List<SurveyItemOption> surveyItemOptions = surveyItemOptionRepository.findBySurveyItem(surveyItem);
//                List<SurveyItemOptionReadDto> surveyItemOptionReadDtos = surveyItemOptions.stream()
//                        .map(SurveyItemOptionReadDto::fromEntity)
//                        .toList();

                for(String responseValue : responseValueList) {
                    if(!optionTextList.contains(responseValue)) {
                        return new ResponseTemplate<StatusEnum>().serverError(StatusEnum.INVALID_OPTION);
                    }
                }
            }
        }

        //응답 엔티티 저장: 새 Response 엔티티를 생성하고 저장합니다.
        String respondentId = responseSaveRequestDto.getRespondentId();
        ResponseSaveDto ResponseSaveDto = new ResponseSaveDto(surveyVersion, respondentId);
        Response response = responseRepository.save(ResponseSaveDto.toEntity());

        //응답 항목 저장: 각 유효한 응답 항목에 대해 ResponseItem 엔티티를 생성하고, Response 엔티티에 연결하여 저장합니다.
        for(int i=0;i<surveyItemReadDtos.size();i++) {
            SurveyItem surveyItem = surveyItems.get(i);
            ResponseItemSaveRequestDto responseItemSaveRequestDto = responseItemSaveRequestDtos.get(i);
            List<String> responseValueList = responseItemSaveRequestDto.getResponseValue();

            for(String responseValue : responseValueList) {
                ResponseItemSaveDto responseItemSaveDto = new ResponseItemSaveDto(surveyItem, response, responseValue);
                responseItemRepository.save(responseItemSaveDto.toEntity());
            }
        }

        return new ResponseTemplate<StatusEnum>().responseOk(StatusEnum.SUCCESS);
    }

//    public void checkInputType(SurveyItemReadDto surveyItemReadDto, ResponseItemSaveRequestDto responseItemSaveRequestDto) {
//
//    }

}
