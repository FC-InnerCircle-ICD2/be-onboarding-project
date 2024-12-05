package com.practice.survey.surveymngt.service;

import com.practice.survey.common.response.ApiResponse;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.surveyItem.model.dto.SurveyItemSaveRequestDto;
import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyItem.repository.SurveyItemRepository;
import com.practice.survey.surveyItemOption.model.dto.SurveyItemOptionSaveRequestDto;
import com.practice.survey.surveyItemOption.model.entity.SurveyItemOption;
import com.practice.survey.surveyItemOption.repository.SurveyItemOptionRepository;
import com.practice.survey.surveyVersion.model.dto.SurveyVersionSaveRequestDto;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import com.practice.survey.surveyVersion.repository.SurveyVersionRepository;
import com.practice.survey.surveymngt.model.dto.SurveyRequestDto;
import com.practice.survey.surveymngt.model.dto.SurveyResponseDto;
import com.practice.survey.surveymngt.model.entity.Survey;
import com.practice.survey.surveymngt.repository.SurveyRepository;
import com.practice.survey.surveymngt.repository.wrapper.SurveyRespositoryWrapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.practice.survey.surveyItem.model.enums.InputType.MULTIPLE_CHOICE;
import static com.practice.survey.surveyItem.model.enums.InputType.SINGLE_CHOICE;

@Service
@RequiredArgsConstructor
@Transactional
public class SurveyServiceImpl implements SurveyService{

    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final SurveyItemOptionRepository surveyItemOptionRepository;
    private final SurveyVersionRepository surveyVersionRepository;
    private final SurveyRespositoryWrapper surveyRespositoryWrapper;

    @Override
    public ApiResponse<StatusEnum> createSurvey(SurveyRequestDto surveyRequestDto) throws NullPointerException {

        int surveyVersionNumber=1; // 최초 생성이므로 1
        List<SurveyItemSaveRequestDto> surveyItemSaveRequestDtos = surveyRequestDto.getSurveyItems();

        // 유효성 체크
        // 1. 설문조사 이름 중복 체크 -> 향후 유효성 체크로 처리 가능한지 확인
        String surveyName= surveyRequestDto.getName();
        if(surveyRepository.existsByName(surveyName)){
            return new ApiResponse<StatusEnum>().serverError(StatusEnum.SURVEY_ALREADY_EXISTS);
        }

        // 2. 설문조사 inputType에 따른 option 체크 -> 향후 유효성 체크로 처리
        for(int i=0; i<surveyItemSaveRequestDtos.size(); i++) {
            SurveyItemSaveRequestDto itemDto = surveyItemSaveRequestDtos.get(i);
            // item의 input_type이 SINGLE_CHOICE, MULTIPLE_CHOICE인 경우에 option 없을 경우 에러 처리
            if (itemDto.getInputType().equals(SINGLE_CHOICE) || itemDto.getInputType().equals(MULTIPLE_CHOICE)) {
                if (itemDto.getOptions().size() == 0) {
                    return new ApiResponse<StatusEnum>().serverError(StatusEnum.OPTION_REQUIRED);
                }
            }
            // 그 외의 경우에는 option이 없으므로, 만약 option이 존재한다면 에러 처리
            else {
                if (itemDto.getOptions() != null) {
                    return new ApiResponse<StatusEnum>().serverError(StatusEnum.OPTION_NOT_REQUIRED);
                }
            }
        }

        // 설문조사 저장
        Survey survey = surveyRequestDto.toEntity();
        surveyRepository.save(survey);

        // 설문조사 버전 저장
        SurveyVersion surveyVersion = saveSurveyVersion(survey, surveyVersionNumber);

        // 설문조사 항목 저장
        saveSurveyItemAndOption(surveyItemSaveRequestDtos, surveyVersion);

        return new ApiResponse<StatusEnum>().responseOk(StatusEnum.SUCCESS);
    }

    @Override
    public ApiResponse<StatusEnum> updateSurvey(SurveyRequestDto surveyRequestDto) throws NullPointerException{

        // 설문조사 이름 기반 설문조사 찾기
        Survey survey = surveyRepository.findByName(surveyRequestDto.getName());

        // 해당 설문조사의 최대 버전 넘버 찾기
        int surveyVersionNumber = surveyVersionRepository.findMaxVersionNumberBySurveyId(survey.getSurveyId());

        // 최대 버전 넘버에 1을 더한 버전 넘버로 저장
        SurveyVersion surveyVersion = saveSurveyVersion(survey, surveyVersionNumber+1);

        List<SurveyItemSaveRequestDto> surveyItemSaveRequestDtos = surveyRequestDto.getSurveyItems();

        // 설문조사 항목 저장
        saveSurveyItemAndOption(surveyItemSaveRequestDtos, surveyVersion);

        return new ApiResponse<StatusEnum>().responseOk(StatusEnum.SUCCESS);
    }

    @Override
    public ApiResponse<List<SurveyResponseDto>> getSurveyResponse(Long surveyId) {

        List<SurveyResponseDto> SurveyResponseDtos = surveyRespositoryWrapper.getSurveyResponse(surveyId);

        return new ApiResponse<List<SurveyResponseDto>>().responseOk(SurveyResponseDtos);
    }

    private SurveyVersion saveSurveyVersion(Survey survey, int surveyVersionNumber){

        SurveyVersionSaveRequestDto surveyVersionSaveRequestDto = SurveyVersionSaveRequestDto.builder()
                .versionNumber(surveyVersionNumber)
                .build();
        SurveyVersion surveyVersion = surveyVersionSaveRequestDto.toEntity(survey,surveyVersionNumber);

        return surveyVersionRepository.save(surveyVersion);
    }

    private void saveSurveyItemAndOption(List<SurveyItemSaveRequestDto> surveyItemSaveRequestDtos, SurveyVersion surveyVersion){

        for(int i=0; i<surveyItemSaveRequestDtos.size(); i++){
            SurveyItemSaveRequestDto itemDto = surveyItemSaveRequestDtos.get(i);

            SurveyItem item = itemDto.toEntity(surveyVersion, i+1);

            surveyItemRepository.save(item);

            // item의 input_type이 SINGLE_CHOICE, MULTIPLE_CHOICE인 경우
            if(itemDto.getInputType().equals(SINGLE_CHOICE) || itemDto.getInputType().equals(MULTIPLE_CHOICE)) {
                // 설문조사 항목의 선택지 생성
                List<SurveyItemOptionSaveRequestDto> optionDtoList = itemDto.getOptions();
                for(int j=0; j<optionDtoList.size(); j++){
                    SurveyItemOptionSaveRequestDto optionDto = optionDtoList.get(j);
                    SurveyItemOption surveyItemOption = optionDto.toEntity(item, j+1);
                    surveyItemOptionRepository.save(surveyItemOption);
                }
            }
        }

    }
}