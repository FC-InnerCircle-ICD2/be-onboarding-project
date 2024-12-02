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
import com.practice.survey.surveymngt.model.dto.SurveySaveRequestDto;
import com.practice.survey.surveymngt.model.entity.Survey;
import com.practice.survey.surveymngt.repository.SurveyRepository;
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

    @Override
    public ApiResponse<StatusEnum> createSurvey(SurveySaveRequestDto surveySaveRequestDto) throws NullPointerException {

        int surveyVersionNumber=1; // 최초 생성이므로 1
        List<SurveyItemSaveRequestDto> surveyItemSaveRequestDtos = surveySaveRequestDto.getSurveyItems();

        // 유효성 체크
        // 1. 설문조사 이름 중복 체크
        String surveyName=surveySaveRequestDto.getName();
        if(surveyRepository.existsByName(surveyName)){
            return new ApiResponse<StatusEnum>().serverError(StatusEnum.SURVEY_ALREADY_EXISTS);
        }

        // 2. 설문조사 inputType에 따른 option 체크
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

        // 설문조사 생성
        Survey survey = surveySaveRequestDto.toEntity();
        surveyRepository.save(survey);

        // 설문조사 버전 생성
        SurveyVersionSaveRequestDto surveyVersionSaveRequestDto = SurveyVersionSaveRequestDto.builder()
                .versionNumber(surveyVersionNumber)
                .build();
        SurveyVersion surveyVersion = surveyVersionSaveRequestDto.toEntity(survey,surveyVersionNumber);
        surveyVersionRepository.save(surveyVersion);

        // 설문조사 항목 생성
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

        return new ApiResponse<StatusEnum>().responseOk(StatusEnum.SUCCESS);
    }
}